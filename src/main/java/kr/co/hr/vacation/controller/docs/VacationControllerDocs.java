package kr.co.hr.vacation.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.global.response.docs.ApiResponseVoid;
import kr.co.hr.vacation.dto.VacationQuotaResponseDTO;
import kr.co.hr.vacation.dto.VacationRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.dto.docs.ApiResponseVacationList;

@Tag(name = "Vacation", description = "휴가 관리 API")
public interface VacationControllerDocs {

    @Operation(summary = "휴가 신청", description = "새로운 휴가를 신청합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "휴가 신청 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVoid.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "휴가 신청 성공",
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
    ResponseEntity<ApiResponse<Void>> requestVacation(@RequestHeader("Authorization") String token, @RequestBody @Valid VacationRequestDTO dto);

    @Operation(summary = "내 휴가 내역 조회", description = "특정 직원의 휴가 신청 내역을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseVacationList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "내 휴가 내역 조회 성공",
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
    ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getMyVacationHistory(@RequestHeader("Authorization") String token);
    
    ResponseEntity<ApiResponse<VacationQuotaResponseDTO>> getMyVacationQuota(String token);
}