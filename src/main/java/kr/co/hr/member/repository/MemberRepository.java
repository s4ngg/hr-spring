package kr.co.hr.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

}
