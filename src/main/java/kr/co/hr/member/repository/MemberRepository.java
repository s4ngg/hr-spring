package kr.co.hr.member.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.hr.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 로그인용
    Optional<Member> findByEmailOrEmployeeNo(String email, String employeeNo);

    // 결근 처리용 - 출근한 직원 제외하고 조회
    List<Member> findByMemberIdNotIn(List<Long> memberIds);

    // 부서 인원 수 조회
    Long countByDepartment_DepartmentId(Long departmentId);

    // 이름 검색 (리스트)
    List<Member> findByNameContaining(String name);

    // 이름 검색 (페이징)
    Page<Member> findByNameContaining(String name, Pageable pageable);
}