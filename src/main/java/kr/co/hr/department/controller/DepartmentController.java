package kr.co.hr.department.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.service.DepartmentService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getDepartmentList() {
        List<DepartmentResponseDto> list = departmentService.getDepartmentList();
        return ResponseEntity.ok(ApiResponse.success("부서 목록 조회 성공", list));
    }

    // 부서 검색
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> searchDepartment(
            @RequestParam("deptName") String deptName) {
        List<DepartmentResponseDto> list = departmentService.searchDepartment(deptName);
        return ResponseEntity.ok(ApiResponse.success("부서 검색 성공", list));
    }

    // 부서 추가 - @Valid 추가!
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDepartment(
            @RequestBody @Valid DepartmentRequestDto dto) {
        departmentService.createDepartment(dto);
        return ResponseEntity.ok(ApiResponse.success("부서 생성 성공"));
    }

    // 부서 수정 - @Valid 추가!
    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> updateDepartment(
            @PathVariable("departmentId") Long departmentId,
            @RequestBody @Valid DepartmentRequestDto dto) {
        departmentService.updateDepartment(departmentId, dto);
        return ResponseEntity.ok(ApiResponse.success("부서 수정 성공"));
    }

    // 부서 삭제
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(
            @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success("부서 삭제 성공"));
    }
}