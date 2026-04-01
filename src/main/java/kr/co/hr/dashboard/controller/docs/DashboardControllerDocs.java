package kr.co.hr.dashboard.controller.docs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.dashboard.dto.DashboardResponseDto;
import kr.co.hr.dashboard.dto.docs.ApiResponseDashboard;
import kr.co.hr.global.response.ApiResponse;

@Tag(name = "Dashboard", description = "대시보드 API")
public interface DashboardControllerDocs {

    @Operation(summary = "대시보드 조회", description = "직원의 대시보드 정보를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseDashboard.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "대시보드 조회 성공",
                        "data": {
                            "memberName": "홍길동",
                            "todayCheckIn": "09:00:00",
                            "todayCheckOut": "18:00:00",
                            "todayStatus": "NORMAL",
                            "remainVacationDays": 12,
                            "totalVacationDays": 15,
                            "monthlyWorkDays": 18,
                            "monthlyTotalDays": 22,
                            "pendingVacationCount": 3,
                            "recentAttendances": [
                                {
                                    "attendanceId": 1,
                                    "workDate": "2026-03-30",
                                    "checkIn": "09:00:00",
                                    "checkOut": "18:00:00",
                                    "workHours": 8.0,
                                    "status": "NORMAL"
                                }
                            ]
                        }
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 직원이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<DashboardResponseDto>> getDashboard(@PathVariable Long memberId);
}