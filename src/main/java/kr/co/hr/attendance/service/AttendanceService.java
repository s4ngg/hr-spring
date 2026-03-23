package kr.co.hr.attendance.service;

import java.util.List;

import kr.co.hr.attendance.dto.AttendanceResponseDTO;

public interface AttendanceService {
	
	List<AttendanceResponseDTO> getAllAttendances();
	
	List<AttendanceResponseDTO> getAttendanceByMember(Long memberId);
}

	