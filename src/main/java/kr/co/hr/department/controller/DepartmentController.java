package kr.co.hr.department.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.department.dto.DepartmentRequestDto;
import kr.co.hr.department.dto.DepartmentResponseDto;
import kr.co.hr.department.service.DepartmentService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Department", description = "부서 관리 API")
@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록 조회
    @Operation(summary = "부서 목록 조회", description = "전체 부서 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> getDepartmentList() {
        List<DepartmentResponseDto> list = departmentService.getDepartmentList();
        return ResponseEntity.ok(ApiResponse.success("부서 목록 조회 성공", list));
    }

    // 부서 검색
    @Operation(summary = "부서 검색", description = "부서명으로 부서를 검색합니다.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<DepartmentResponseDto>>> searchDepartment(
            @RequestParam("deptName") String deptName) {
        List<DepartmentResponseDto> list = departmentService.searchDepartment(deptName);
        return ResponseEntity.ok(ApiResponse.success("부서 검색 성공", list));
    }

    // 부서 추가 - @Valid 추가!
    @Operation(summary = "부서 생성", description = "새로운 부서를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createDepartment(
            @RequestBody @Valid DepartmentRequestDto dto) {
        departmentService.createDepartment(dto);
        return ResponseEntity.ok(ApiResponse.success("부서 생성 성공"));
    }

    // 부서 수정 - @Valid 추가!
    @Operation(summary = "부서 수정", description = "부서 정보를 수정합니다.")
    @PutMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> updateDepartment(
            @PathVariable("departmentId") Long departmentId,
            @RequestBody @Valid DepartmentRequestDto dto) {
        departmentService.updateDepartment(departmentId, dto);
        return ResponseEntity.ok(ApiResponse.success("부서 수정 성공"));
    }

    // 부서 삭제
    @Operation(summary = "부서 삭제", description = "부서를 삭제합니다.")
    @DeleteMapping("/{departmentId}")
    public ResponseEntity<ApiResponse<Void>> deleteDepartment(
            @PathVariable("departmentId") Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(ApiResponse.success("부서 삭제 성공"));
    }
}