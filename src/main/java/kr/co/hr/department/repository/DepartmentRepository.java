package kr.co.hr.department.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.co.hr.department.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // 부서명으로 검색
    List<Department> findByDeptNameContaining(String deptName);
}