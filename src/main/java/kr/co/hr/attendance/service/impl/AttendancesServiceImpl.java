package kr.co.hr.attendance.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.attendance.service.AttendanceService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendancesServiceImpl implements AttendanceService {

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

        Attendance attendance = Attendance.checkIn(
                member,
                LocalDate.now(),
                LocalTime.now()
        );

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

    @Transactional
    public void processAbsent(LocalDate date) {
        List<Long> attendedMemberIds = attendanceRepository
                .findMemberIdsByWorkDate(date);

        List<Member> absentMembers = memberRepository
                .findByMemberIdNotIn(attendedMemberIds);

        List<Attendance> absentList = absentMembers.stream()
                .map(m -> Attendance.absent(m, date))
                .collect(Collectors.toList());

        attendanceRepository.saveAll(absentList);
    }
}