package kr.co.hr.vacation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co.hr.vacation.entity.Vacation;
import kr.co.hr.vacation.enums.VacationStatus;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
	// 1. 특정 직원의 연차별 휴가 내역 조회 (최신순)
    List<Vacation> findByMember_MemberIdOrderByCreatedAtDesc(Long memberId);

    // 2. 승인대기 중인 휴가 목록 조회 (관리자용)
    @Query("select v from Vacation v join fetch v.member where v.status = :status")
    List<Vacation> findByStatusWithMember(@Param("status") VacationStatus status);
    
    int countByStatusAndMember_MemberId(VacationStatus status, Long memberId);
} 
