package kr.co.hr.member.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	List<Member> findByNameContaining(String name);
	
	Page<Member> findByNameContaining(String name, Pageable pageable);
}