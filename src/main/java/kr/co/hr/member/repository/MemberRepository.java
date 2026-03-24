package kr.co.hr.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	List<Member> findByNameContaining(String name);
}