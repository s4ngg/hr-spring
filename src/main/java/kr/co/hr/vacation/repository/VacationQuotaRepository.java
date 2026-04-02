package kr.co.hr.vacation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.hr.vacation.entity.VacationQuota;

public interface VacationQuotaRepository extends JpaRepository<VacationQuota, Long>{
	// 특정 회원의 올해 휴가 잔여량 정보 가져오기
	// 휴가를 신청할 자격이 있는지 확인하는 로직
	@Query("SELECT vq FROM VacationQuota vq WHERE vq.member.memberId = :memberId AND vq.year = :year")
	Optional<VacationQuota> findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") Integer year);
	
}
