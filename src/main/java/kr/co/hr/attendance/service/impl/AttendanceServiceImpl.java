package kr.co.hr.attendance.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {
	
	private final AttendanceRepository attendanceRepository;
	
	@Override
	public List<AttendanceResponseDTO> getAllAttendances() {
		return attendanceRepository.findAll()
				.stream()
				.map(AttendanceResponseDTO::new)
				.toList();
	}

	@Override
	public List<AttendanceResponseDTO> getAttendanceByMember(Long memberId) {
		return attendanceRepository.findByMember_MemberId(memberId)
				.stream()
				.map(AttendanceResponseDTO::new)
				.toList();
	}
}
