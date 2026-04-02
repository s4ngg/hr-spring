package kr.co.hr.vacation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.hr.vacation.entity.VacationQuota;

public interface VacationQuotaRepository extends JpaRepository<VacationQuota, Long>{
    @Query("SELECT vq FROM VacationQuota vq WHERE vq.member.memberId = :memberId AND vq.year = :year")
    Optional<VacationQuota> findByMemberIdAndYear(@Param("memberId") Long memberId, @Param("year") Integer year);
}
