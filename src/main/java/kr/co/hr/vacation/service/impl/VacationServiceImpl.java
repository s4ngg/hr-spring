package kr.co.hr.vacation.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.dto.VacationRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.entity.Vacation;
import kr.co.hr.vacation.entity.VacationQuota;
import kr.co.hr.vacation.enums.VacationStatus;
import kr.co.hr.vacation.repository.VacationQuotaRepository;
import kr.co.hr.vacation.repository.VacationRepository;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class VacationServiceImpl implements VacationService{
	
	private final VacationRepository vacationRepository;
    private final VacationQuotaRepository quotaRepository;
    private final MemberRepository memberRepository;
    // 휴가 신청 로직
    @Override
    @Transactional
    public void requestVacation(VacationRequestDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("직원 정보를 찾을 수 없습니다."));

        int currentYear = LocalDate.now().getYear();
        VacationQuota quota = quotaRepository.findByMember_MemberIdAndYear(dto.getMemberId(), currentYear)
                .orElseThrow(() -> new RuntimeException("올해 배정된 휴가 정보가 없습니다."));
        
        if (!quota.getMember().getMemberId().equals(member.getMemberId())) {
            throw new RuntimeException("휴가 신청자의 quota 정보가 일치하지 않습니다.");
        }

        if (dto.getStartDate().isAfter(dto.getEndDate())) {
            throw new RuntimeException("휴가 시작일은 종료일보다 빨라야 합니다.");
        }

        int approvedUsedDays = quota.getUsedDays() != null ? quota.getUsedDays() : 0;
        int totalDays = quota.getTotalDays() != null ? quota.getTotalDays() : 0;

        List<Vacation> pendingVacations =
                vacationRepository.findByMember_MemberIdAndStatus(dto.getMemberId(), VacationStatus.PENDING);

        int pendingDays = pendingVacations.stream()
                .mapToInt(v -> (int) (java.time.temporal.ChronoUnit.DAYS.between(v.getStartDate(), v.getEndDate()) + 1))
                .sum();

        Long requestDays = dto.getDays();

        if (approvedUsedDays + pendingDays + requestDays > totalDays) {
            throw new RuntimeException("잔여 휴가가 부족합니다.");
        }

        Vacation vacation = Vacation.builder()
                .member(member)
                .vacationQuota(quota)
                .vacationType(dto.getVacationType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status(VacationStatus.PENDING)
                .build();

        vacationRepository.save(vacation);
    }
    
    
    
    
    
    
    // 휴가 신청 내역 조회 로직
    @Override
    @Transactional(readOnly = true)
    public List<VacationResponseDTO> getMyVacationHistory(Long memberId) {
        List<Vacation> vacations = vacationRepository.findByMember_MemberIdOrderByCreatedAtDesc(memberId);
        
        return vacations.stream().map(v -> {
           
            long dayDiff = java.time.temporal.ChronoUnit.DAYS.between(v.getStartDate(), v.getEndDate()) + 1;

            return VacationResponseDTO.builder()
                    .vacationId(v.getVacationId())
                    .memberName(v.getMember().getName())
                    .vacationType(v.getVacationType())
                    .startDate(v.getStartDate())
                    .endDate(v.getEndDate())
                    .days((int) dayDiff)
                    .status(v.getStatus().name())
                    .createdAt(v.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<VacationResponseDTO> getPendingVacations() {
    	// 상태가 '펜딩' 상태인것들만 가져오기
    	List<Vacation> pendingList = vacationRepository.findByStatusWithMember(VacationStatus.PENDING);    	
    	return pendingList.stream().map(v -> {
            // 날짜 차이 계산
            long dayDiff = java.time.temporal.ChronoUnit.DAYS.between(v.getStartDate(), v.getEndDate()) + 1;
            // 자바의 LocalDate는 단순히 '날짜' 정보만 가진 객체라, endDate - startDate 같은 산술 연산
            // 그래서 "두 날짜 사이의 단위를 계산해주는 도구(ChronoUnit) 사용

            return VacationResponseDTO.builder()
                    .vacationId(v.getVacationId())
                    .memberName(v.getMember().getName()) 
                    .vacationType(v.getVacationType())
                    .startDate(v.getStartDate())
                    .endDate(v.getEndDate())
                    .days((int) dayDiff)
                    .status(v.getStatus().name())
                    .createdAt(v.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }
    
    
    
    @Override
    @Transactional
    public void updateVacationStatus(Long vacationId, VacationAdminRequestDTO dto) {
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new RuntimeException("해당 휴가 신청 건을 찾을 수 없습니다."));

        if (vacation.getStatus() != VacationStatus.PENDING) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 처리 완료된 신청 건입니다.");
        }

        VacationStatus newStatus;
        try {
            newStatus = VacationStatus.valueOf(dto.getStatus());
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 상태값입니다.");
        }

        vacation.setStatus(newStatus);

        if (newStatus == VacationStatus.APPROVED) {
            if (vacation.getStartDate() == null || vacation.getEndDate() == null) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "휴가 날짜 정보가 올바르지 않습니다.");
            }

            long days = java.time.temporal.ChronoUnit.DAYS.between(
                    vacation.getStartDate(),
                    vacation.getEndDate()
            ) + 1;

            VacationQuota quota = vacation.getVacationQuota();
            if (quota == null) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "연차 정보(quota)를 찾을 수 없습니다.");
            }

            int currentUsedDays = quota.getUsedDays() != null ? quota.getUsedDays() : 0;
            int totalDays = quota.getTotalDays() != null ? quota.getTotalDays() : 0;

            if (currentUsedDays + days > totalDays) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "연차 일수를 초과했습니다.");
            }
            quota.setUsedDays(currentUsedDays + (int) days);
           
        }
    	
    	// 5. 반려(REJECTED) 시 사유 저장 
        // if (newStatus == VacationStatus.REJECTED) {
        //     vacation.setRejectReason(dto.getRejectReason());
        // }
    	

    
    
    }
    
    
}
