package kr.co.hr.attendance.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.hr.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
	
	// 특정 직원 근태 전체 조회
	List<Attendance> findByMember_MemberId(Long memberId);
	
	// 특정 날짜 전체 근태 조회
	List<Attendance> findByWorkDate(LocalDate date);
	
	// 특정 직원 + 특정 날짜 근태 조회
	Optional<Attendance> findByMemberIdAndWorkDate(Long memberId, LocalDate date);
	
	// 특정 직원 + 기간별 근태 조회
	List<Attendance> findByMember_MemberIdAndWorkDateBetween(Long memberId, LocalDate start, LocalDate end);

	// 대시보드용 추가
	Optional<Attendance> findTopByMember_MemberIdAndWorkDateOrderByCheckInAsc(Long memberId, LocalDate date);
	int countByMemberIdAndWorkDateBetween(Long memberId, LocalDate start, LocalDate end);
	List<Attendance> findTop5ByMemberIdOrderByWorkDateDesc(Long memberId);
	
	boolean existsByMember_MemberIdAndWorkDate(Long memberId, LocalDate date);
	
	// 특정 날짜에 출근한 직원 ID 목록 조회
	@Query("SELECT a.member.memberId FROM Attendance a WHERE a.workDate = :date")
	List<Long> findMemberIdsByWorkDate(@Param("date") LocalDate date);
}
