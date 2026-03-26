package kr.co.hr.vacation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse; // 배운 위치대로 임포트
import kr.co.hr.vacation.dto.VacationRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Vacation", description = "휴가 관리 API")
@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;

    // 1. 휴가 신청
    @Operation(summary = "휴가 신청", description = "새로운 휴가를 신청합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> requestVacation(
            @RequestBody @Valid VacationRequestDTO dto) {
        vacationService.requestVacation(dto);
        return ResponseEntity.ok(ApiResponse.success("휴가 신청 성공"));
    }

    // 2. 내 휴가 내역 조회
    @Operation(summary = "내 휴가 내역 조회", description = "특정 직원의 휴가 신청 내역을 조회합니다.")
    @GetMapping("/my/{memberId}")
    public ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getMyVacationHistory(
            @PathVariable("memberId") Long memberId) {
        List<VacationResponseDTO> list = vacationService.getMyVacationHistory(memberId);
        return ResponseEntity.ok(ApiResponse.success("내 휴가 내역 조회 성공", list));
    }
}