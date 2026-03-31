package kr.co.hr.dashboard.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hr.dashboard.controller.docs.DashboardControllerDocs;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.dashboard.service.DashboardService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController implements DashboardControllerDocs {

    private final DashboardService dashboardService;

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<DashboardResponseDto>> getDashboard(
            @PathVariable("memberId") Long memberId) {
    	return ApiResponse.success("대시보드 조회 성공",
                dashboardService.getDashboard(memberId));
    }
}