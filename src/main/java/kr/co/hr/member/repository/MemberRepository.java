package kr.co.hr.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	
	
	Optional<Member> findByEmail(String email);
	
	// 이메일이 or 사번으로 회원 찾기
	Optional<Member> findByEmailOrEmployeeNo(String Email, String employeeNo);

	
}
