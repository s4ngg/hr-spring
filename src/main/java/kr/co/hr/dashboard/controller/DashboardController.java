package kr.co.hr.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.")
    })
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<DashboardResponseDto>> getDashboard(
            @PathVariable("memberId") Long memberId) {
    	return ApiResponse.success("대시보드 조회 성공",
                dashboardService.getDashboard(memberId));
    }
}