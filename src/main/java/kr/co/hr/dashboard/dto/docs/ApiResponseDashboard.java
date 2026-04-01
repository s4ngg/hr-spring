package kr.co.hr.dashboard.dto.docs;

import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.global.response.ApiResponse;

public class ApiResponseDashboard extends ApiResponse<DashboardResponseDto> {
    public ApiResponseDashboard(boolean success, String message, DashboardResponseDto data) {
        super(success, message, data);
    }
}