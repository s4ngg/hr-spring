package kr.co.hr.department.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록 조회
    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getDepartmentList() {
        return ResponseEntity.ok(departmentService.getDepartmentList());
    }

    // 부서 검색
    @GetMapping("/search")
    public ResponseEntity<List<DepartmentResponseDto>> searchDepartment(
            @RequestParam String deptName) {
        return ResponseEntity.ok(departmentService.searchDepartment(deptName));
    }

    // 부서 추가
    @PostMapping
    public ResponseEntity<Void> createDepartment(
            @RequestBody DepartmentRequestDto dto) {
        departmentService.createDepartment(dto);
        return ResponseEntity.ok().build();
    }

    // 부서 수정
    @PutMapping("/{departmentId}")
    public ResponseEntity<Void> updateDepartment(
            @PathVariable Long departmentId,
            @RequestBody DepartmentRequestDto dto) {
        departmentService.updateDepartment(departmentId, dto);
        return ResponseEntity.ok().build();
    }

    // 부서 삭제
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok().build();
    }
}