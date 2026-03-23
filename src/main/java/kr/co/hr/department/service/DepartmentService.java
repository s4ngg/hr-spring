package kr.co.hr.department.service;

import java.util.List;
import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;

public interface DepartmentService {

    // 부서 목록 조회
    List<DepartmentResponseDto> getDepartmentList();

    // 부서 검색
    List<DepartmentResponseDto> searchDepartment(String deptName);

    // 부서 추가
    void createDepartment(DepartmentRequestDto dto);

    // 부서 수정
    void updateDepartment(Long departmentId, DepartmentRequestDto dto);

    // 부서 삭제
    void deleteDepartment(Long departmentId);
}