package kr.co.hr.attendance.service;

import java.time.LocalDate;
import java.util.List;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;

public interface AttendanceService {

    List<AttendanceResponseDTO> getAllAttendances();

    AttendanceResponseDTO checkIn(AttendanceRequestDTO requestDTO);

    AttendanceResponseDTO checkOut(Long attendanceId, AttendanceRequestDTO requestDTO);

    void deleteAttendance(Long attendanceId);

    List<AttendanceResponseDTO> getAttendancesByMember(Long memberId);

    void processAbsent(LocalDate date);
}