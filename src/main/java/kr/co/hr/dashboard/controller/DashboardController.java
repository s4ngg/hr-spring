package kr.co.hr.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.dashboard.service.DashboardService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "Dashboard", description = "대시보드 API")
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "대시보드 조회", description = "직원의 대시보드 정보를 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<DashboardResponseDto>> getDashboard(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(ApiResponse.success("대시보드 조회 성공",
                dashboardService.getDashboard(memberId)));
    }
}