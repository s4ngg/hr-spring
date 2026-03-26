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
import kr.co.hr.vacation.repository.VacationQuotaRepository;
import kr.co.hr.vacation.repository.VacationRepository;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;



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
    	// 멤버 레포 필요             신청자 정보 가져오기
    	Member member = memberRepository.findById(dto.getMemberId()) 
    			.orElseThrow(() -> new RuntimeException("직원 정보를 찾을 수 없습니다."));
    	
    	//잔여 휴가 정보 가져오기
    	int currentYear = LocalDate.now().getYear();
    	VacationQuota quota = quotaRepository.findByMember_MemberIdAndYear(dto.getMemberId(), currentYear)
                .orElseThrow(() -> new RuntimeException("올해 배정된 휴가 정보가 없습니다."));
    	
    	
    	// 잔여 일수 검증하기 (남은 개수보다 신청한 갯수가 많으면 에러!)
    	int remainingDays = quota.getTotalDays() - quota.getUsedDays();
    	
    	if (dto.getStartDate().isAfter(dto.getEndDate())) {
    	    throw new RuntimeException("휴가 시작일은 종료일보다 빨라야 합니다.");
    	}
    	
        if (remainingDays < dto.getDays()) {
            throw new RuntimeException("잔여 휴가가 부족합니다.");
        	}
        
        
        
        
        Vacation vacation = Vacation.builder()
                .member(member)
                .vacationQuota(quota)
                .vacationType(dto.getVacationType())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .status("PENDING")
                .build();
        
        vacationRepository.save(vacation);
    }
    
    
    
    
    
    
    // 휴가 신청 내역 조회 로직
    @Override
    @Transactional(readOnly = true)
    public List<VacationResponseDTO> getMyVacationHistory(Long memberId){
    
    List<Vacation> vacations = vacationRepository.findByMember_MemberIdOrderByCreatedAtDesc(memberId);
    
    return vacations.stream().map(v -> VacationResponseDTO.builder()
                    .vacationId(v.getVacationId())
                    .vacationType(v.getVacationType())
                    .startDate(v.getStartDate())
                    .endDate(v.getEndDate())
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .build())
            .collect(Collectors.toList());
    }
    
    
    @Override
    @Transactional(readOnly = true)
    public List<VacationResponseDTO> getPendingVacations() {
    	// 상태가 '펜딩' 상태인것들만 가져오기
    	List<Vacation> pendingList = vacationRepository.findByStatusWithMember("PENDING");
    	
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
                    .status(v.getStatus())
                    .createdAt(v.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());
    }
    
    
    
   @Override
   @Transactional
    public void updateVacationStatus(Long vacationId, VacationAdminRequestDTO dto) {
    	Vacationvacation = vacationRepository.findByid(vacationId)
    		
    	
    }
    
    
    
    
    
}
