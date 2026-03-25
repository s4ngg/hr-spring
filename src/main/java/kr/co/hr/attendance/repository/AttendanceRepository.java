package kr.co.hr.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	// 특정 직원 근태 전체 조회
	List<Attendance> findByMember_MemberId(Long memberId);
	
	// 특정 날짜 전체 근태 조회
	List<Attendance> findByWorkDate(LocalDate date);
	
	// 특정 직원 + 특정 날짜 근태 조회
	List <Attendance> findByMember_MemberIdAndWorkDate(Long memberId, LocalDate date);
	
	// 특정 직원 + 기간별 근태 조회
	List<Attendance> findByMember_MemberIdAndWorkDateBetween(Long memberId, LocalDate start, LocalDate end);

	// 특정 직원 status별 count
	long countByMember_MemberIdAndStatus(Long memberId, String status);

	// 특정 직원 이번달 출근 일수
	long countByMember_MemberIdAndWorkDateBetween(Long memberId, LocalDate start, LocalDate end);
}
