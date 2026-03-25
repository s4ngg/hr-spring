package kr.co.hr.attendance.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class AttendancesServiceImpl implements AttendanceService {  // ✅ Attendances → Attendance (오타 수정)

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAllAttendances() {
        return attendanceRepository.findAll()
                .stream()
                .map(AttendanceResponseDTO::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceResponseDTO> getAttendancesByMember(Long memberId) {
        return attendanceRepository.findByMember_MemberId(memberId)
                .stream()
                .map(AttendanceResponseDTO::new)
                .toList();
    }

    @Override
    @Transactional
    public AttendanceResponseDTO checkIn(AttendanceRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("해당 직원이 없습니다."));

        LocalTime now = LocalTime.now();
        LocalTime nineAM = LocalTime.of(9, 0);
        String status = now.isAfter(nineAM) ? "LATE" : "NORMAL";

        Attendance attendance = new Attendance();
        attendance.setMember(member);
        attendance.setWorkDate(LocalDate.now());
        attendance.setCheckIn(now);
        attendance.setStatus(status);

        return new AttendanceResponseDTO(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public AttendanceResponseDTO checkOut(Long attendanceId, AttendanceRequestDTO requestDTO) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("근태 기록을 찾을 수 없습니다."));
        attendance.checkOut();
        return new AttendanceResponseDTO(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
    
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정 실행
    @Transactional
    public void processAbsent() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        
        // 모든 직원 조회
        List<Member> allMembers = memberRepository.findAll();
        
        for (Member member : allMembers) {
            // 어제 출근 기록 없으면 ABSENT 처리
            boolean hasAttendance = attendanceRepository
                    .existsByMember_MemberIdAndWorkDate(member.getMemberId(), yesterday);
            
            if (!hasAttendance) {
                Attendance absent = new Attendance();
                absent.setMember(member);
                absent.setWorkDate(yesterday);
                absent.setStatus("ABSENT");
                attendanceRepository.save(absent);
            }
        }
    }
}