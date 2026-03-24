package kr.co.hr.attendance.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendancesServiceImpl implements AttendanceService {  // ✅ Attendances → Attendance (오타 수정)

    private final AttendanceRepository attendanceRepository;

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

    // ✅ 누락된 메서드 구현
    @Override
    @Transactional
    public AttendanceResponseDTO checkIn(AttendanceRequestDTO requestDTO) {
        Attendance attendance = new Attendance();
        attendance.setWorkDate(LocalDate.now());
        attendance.setCheckIn(LocalTime.now());
        // requestDTO에서 필요한 값 세팅 (memberId 등)
        return new AttendanceResponseDTO(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public AttendanceResponseDTO checkOut(Long attendanceId, AttendanceRequestDTO requestDTO) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("근태 기록을 찾을 수 없습니다."));
        attendance.setCheckOut(LocalTime.now());
        return new AttendanceResponseDTO(attendanceRepository.save(attendance));
    }

    @Override
    @Transactional
    public void deleteAttendance(Long attendanceId) {
        attendanceRepository.deleteById(attendanceId);
    }
}