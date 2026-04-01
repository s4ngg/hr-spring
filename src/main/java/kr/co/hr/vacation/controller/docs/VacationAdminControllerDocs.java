package kr.co.hr.vacation.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.global.response.docs.ApiResponseVoid;
import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.dto.docs.ApiResponseVacationList;

@Tag(name = "Vacation Admin", description = "관리자 전용 휴가 승인/반려 API")
public interface VacationAdminControllerDocs {

    @Operation(summary = "승인 대기 목록 조회", description = "관리자가 승인해야 할 휴가 목록을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVacationList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "승인 대기 목록 조회 성공",
                        "data": [
                            {
                                "vacationId": 1,
                                "memberName": "홍길동",
                                "vacationType": "연차",
                                "startDate": "2026-03-23",
                                "endDate": "2026-03-30",
                                "days": 8,
                                "status": "보류중",
                                "createdAt": "2026-03-21T00:00:00"
                            }
                        ]
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getPendingVacations();

    @Operation(summary = "휴가 상태 변경", description = "관리자가 특정 휴가 신청을 승인하거나 반려합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "상태 변경 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "휴가 상태 변경 처리가 완료되었습니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "해당 휴가 신청이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 휴가 신청이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<ApiResponse<Void>> updateVacationStatus(@PathVariable Long vacationId, @RequestBody VacationAdminRequestDTO dto);
}