package kr.co.hr.attendance.controller.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.dto.docs.ApiResponseAttendance;
import kr.co.hr.attendance.dto.docs.ApiResponseAttendanceList;

@Tag(name = "Attendance", description = "근태 관련 API")
public interface AttendanceControllerDocs {

    @Operation(summary = "전체 근태 조회", description = "모든 직원의 근태 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseAttendanceList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "조회 성공",
                        "data": [
                            {
                                "attendanceId": 1,
                                "memberId": 1,
                                "workDate": "2026-03-31",
                                "checkIn": "09:00:00",
                                "checkOut": "18:00:00",
                                "workHours": 8.0,
                                "status": "NORMAL"
                            }
                        ]
                    }
                """)
            ))
    })
    ResponseEntity<List<AttendanceResponseDTO>> getAllAttendances();

    @Operation(summary = "특정 직원 근태 조회", description = "직원 ID로 해당 직원의 근태 목록을 조회합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseAttendanceList.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "조회 성공",
                        "data": [
                            {
                                "attendanceId": 1,
                                "memberId": 1,
                                "workDate": "2026-03-31",
                                "checkIn": "09:00:00",
                                "checkOut": "18:00:00",
                                "workHours": 8.0,
                                "status": "NORMAL"
                            }
                        ]
                    }
                """)
            )),
        @ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
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
    ResponseEntity<List<AttendanceResponseDTO>> getAttendancesByMember(@PathVariable Long memberId);

    @Operation(summary = "출근 체크인", description = "직원의 출근을 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "체크인 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseAttendance.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "체크인 성공",
                        "data": {
                            "attendanceId": 1,
                            "memberId": 1,
                            "workDate": "2026-03-31",
                            "checkIn": "09:00:00",
                            "checkOut": null,
                            "workHours": null,
                            "status": "NORMAL"
                        }
                    }
                """)
            )),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            )),
        @ApiResponse(responseCode = "404", description = "해당 직원이 없습니다.",
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
    ResponseEntity<AttendanceResponseDTO> checkIn(@RequestBody AttendanceRequestDTO requestDTO);

    @Operation(summary = "퇴근 체크아웃", description = "특정 근태 기록에 퇴근 시간을 등록합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "체크아웃 성공",
            content = @Content(
                schema = @Schema(implementation = ApiResponseAttendance.class),
                examples = @ExampleObject(value = """
                    {
                        "success": true,
                        "message": "체크아웃 성공",
                        "data": {
                            "attendanceId": 1,
                            "memberId": 1,
                            "workDate": "2026-03-31",
                            "checkIn": "09:00:00",
                            "checkOut": "18:00:00",
                            "workHours": 8.0,
                            "status": "NORMAL"
                        }
                    }
                """)
            )),
        @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "잘못된 요청입니다.",
                        "data": null
                    }
                """)
            )),
        @ApiResponse(responseCode = "404", description = "해당 근태 기록이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 근태 기록이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<AttendanceResponseDTO> checkOut(@PathVariable Long attendanceId, @RequestBody AttendanceRequestDTO requestDTO);

    @Operation(summary = "근태 삭제", description = "특정 근태 기록을 삭제합니다.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "해당 근태 기록이 없습니다.",
            content = @Content(
                examples = @ExampleObject(value = """
                    {
                        "success": false,
                        "message": "해당 근태 기록이 없습니다.",
                        "data": null
                    }
                """)
            ))
    })
    ResponseEntity<Void> deleteAttendance(@PathVariable Long attendanceId);
}