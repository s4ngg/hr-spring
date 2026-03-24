package kr.co.hr.attendance.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.entity.Attendance;
import kr.co.hr.attendance.repository.AttendanceRepository;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceService {

	private final AttendanceRepository attendanceRepository;
	private final MemberRepository memberRepository;
	
//	전체 근태 조회
	public List<AttendanceResponseDTO> getAllAttendances() {
		return attendanceRepository.findAll()
				.stream()
				.map(AttendanceResponseDTO::new)
				.collect(Collectors.toList());
	}
	
//	특정 직원 근태 조회
	public List<AttendanceResponseDTO> getAttendancesByMember(Long memberId) {
		return attendanceRepository.findByMember_MemberId(memberId)
				.stream()
				.map(AttendanceResponseDTO::new)
				.collect(Collectors.toList());
	}
	
//	특정 직원 기간별 근태 조회
	public List<AttendanceResponseDTO> getAttendancesByMemberAndPeriod(Long memberId, LocalDate start, LocalDate end ) {
		return attendanceRepository.findByMember_MemberIdAndWorkDateBetween(memberId, start, end)
				.stream()
				.map(AttendanceResponseDTO::new)
				.collect(Collectors.toList());
	}
//	출근 체크인
	@Transactional
	public AttendanceResponseDTO checkIn(AttendanceRequestDTO requestDTO) {
		Member member = memberRepository.findById(requestDTO.getMemberId())
				.orElseThrow(() -> new RuntimeException("직원을 찾을 수 없습니다. id:" + requestDTO.getMemberId()));
		
		Attendance attendance = new Attendance();
		attendance.setMember(member);
		attendance.setMemberId(requestDTO.getMemberId());
		attendance.setWorkDate(requestDTO.getWorkDate());
		attendance.setCheckIn(requestDTO.getCheckIn());
		attendance.setStatus(requestDTO.getStatus());
	
		return new AttendanceResponseDTO(attendanceRepository.save(attendance));
	}
//		퇴근 체크아웃
		@Transactional
		public AttendanceResponseDTO checkOut(Long attendanceId, AttendanceRequestDTO requestDTO ) {
			Attendance attendance = attendanceRepository.findById(attendanceId)
					.orElseThrow(() -> new RuntimeException("근태 기록을 찾을 수 없습니다. id: " + attendanceId));
			
			attendance.setCheckOut(requestDTO.getCheckOut());
			attendance.setWorkHours(requestDTO.getWorkHours());
			attendance.setStatus(requestDTO.getStatus());
			
			return new AttendanceResponseDTO(attendance);
		}
//		근태 삭제
		@Transactional
		public void deleteAttendance(Long attendanceId) {
			Attendance attendance = attendanceRepository.findById(attendanceId)
					.orElseThrow(() -> new RuntimeException("근태 기록을 찾을 수 없습니다. id: " + attendanceId));
					attendanceRepository.delete(attendance);
		}
		}