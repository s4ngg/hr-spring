package kr.co.hr.vacation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.vacation.entity.Vacation;

public interface VacationRepository extends JpaRepository<Vacation, Long> {
	// 1. 특정 직원의 연차별 휴가 내역 조회 (최신순)
    List<Vacation> findByMember_MemberIdOrderByCreatedAtDesc(Long memberId);

    // 2. 승인대기 중인 휴가 목록 조회 (관리자용)
    List<Vacation> findByStatus(String status);
    
    int countByMember_MemberIdAndStatus(Long memberId, String status);
} 
