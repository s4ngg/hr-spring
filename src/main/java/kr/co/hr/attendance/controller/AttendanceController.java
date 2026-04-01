package kr.co.hr.attendance.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.service.AttendanceService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "근태 관리", description = "근태 관련 API")
@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;

    @Operation(summary = "전체 근태 조회", description = "모든 직원의 근태 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceResponseDTO>>> getAllAttendances() {
        return ApiResponse.success("전체 근태 조회 성공", attendanceService.getAllAttendances());
    }

    @Operation(summary = "특정 직원 근태 조회", description = "직원 ID로 해당 직원의 근태 목록을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponseDTO>>> getAttendancesByMember(
            @PathVariable("memberId") Long memberId) {
        return ApiResponse.success("직원 근태 조회 성공", attendanceService.getAttendancesByMember(memberId));
    }

    @Operation(summary = "출근 체크인", description = "직원의 출근을 등록합니다.")
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> checkIn(
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ApiResponse.success("체크인 성공", attendanceService.checkIn(requestDTO));
    }

    @Operation(summary = "퇴근 체크아웃", description = "특정 근태 기록에 퇴근 시간을 등록합니다.")
    @PatchMapping("/check-out/{attendanceId}")
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> checkOut(
            @PathVariable("attendanceId") Long attendanceId,
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ApiResponse.success("체크아웃 성공", attendanceService.checkOut(attendanceId, requestDTO));
    }

    @Operation(summary = "근태 삭제", description = "특정 근태 기록을 삭제합니다.")
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(
            @PathVariable("attendanceId") Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ApiResponse.success("근태 삭제 성공");
    }
}