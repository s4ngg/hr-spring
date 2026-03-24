package kr.co.hr.vacation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.vacation.entity.VacationQuota;

public interface VacationQuotaRepository extends JpaRepository<VacationQuota, Long>{
	// 특정 회원의 올해 휴가 잔여량 정보 가져오기
	// 휴가를 신청할 자격이 있는지 확인하는 로직
	Optional<VacationQuota> findByMember_MemberIdAndYear(Long memberId, Integer year);
	
}
