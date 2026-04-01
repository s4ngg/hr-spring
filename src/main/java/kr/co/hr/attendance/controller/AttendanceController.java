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
import kr.co.hr.attendance.controller.docs.AttendanceControllerDocs;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.service.AttendanceService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "근태 관리", description = "근태 관련 API")
@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController implements AttendanceControllerDocs {
    private final AttendanceService attendanceService;
   
    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<AttendanceResponseDTO>>> getAllAttendances() {
        return ApiResponse.success("전체 근태 조회 성공", attendanceService.getAllAttendances());
    }

    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponseDTO>>> getAttendancesByMember(
            @PathVariable("memberId") Long memberId) {
        return ApiResponse.success("직원 근태 조회 성공", attendanceService.getAttendancesByMember(memberId));
    }

    @Override
    @PostMapping("/check-in")
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> checkIn(
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ApiResponse.success("체크인 성공", attendanceService.checkIn(requestDTO));
    }

    @Override
    @PatchMapping("/check-out/{attendanceId}")
    public ResponseEntity<ApiResponse<AttendanceResponseDTO>> checkOut(
            @PathVariable("attendanceId") Long attendanceId,
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ApiResponse.success("체크아웃 성공", attendanceService.checkOut(attendanceId, requestDTO));
    }

    @Override
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<ApiResponse<Void>> deleteAttendance(
            @PathVariable("attendanceId") Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ApiResponse.success("근태 삭제 성공");
    }
}