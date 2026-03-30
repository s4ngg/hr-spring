package kr.co.hr.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인용
    Optional<Member> findByEmailOrEmployeeNo(String email, String employeeNo);
    // 결근 처리용 - 출근한 직원 제외하고 조회
    List<Member> findByMemberIdNotIn(List<Long> memberIds);
    // 부서 인원 수 조회
    Long countByDepartment_DepartmentId(Long departmentId);
    
    Optional<Member> findByEmail(String email);
}