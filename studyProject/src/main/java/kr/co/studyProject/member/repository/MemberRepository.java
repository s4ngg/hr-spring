package kr.co.studyProject.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.studyProject.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	boolean existsByUserName(String userName); 
	boolean existsByEmail(String email);
	Member findByUserId(String userId);
}
