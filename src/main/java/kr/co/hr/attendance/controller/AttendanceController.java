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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.co.hr.attendance.controller.docs.AttendanceControllerDocs;
import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
public class AttendanceController implements AttendanceControllerDocs {

    private final AttendanceService attendanceService;

    // 전체 근태 조회
    @Override
    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    // 특정 직원 근태 조회
    @Override
    @GetMapping("/{memberId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendancesByMember(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(attendanceService.getAttendancesByMember(memberId));
    }

    // 출근 체크인
    @Override
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDTO> checkIn(
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ResponseEntity.ok(attendanceService.checkIn(requestDTO));
    }

    // 퇴근 체크아웃
    @Override
    @PutMapping("/check-out/{attendanceId}")
    public ResponseEntity<AttendanceResponseDTO> checkOut(
            @PathVariable("attendanceId")Long attendanceId,
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ResponseEntity.ok(attendanceService.checkOut(attendanceId, requestDTO));
    }

    // 근태 삭제
    @Override
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable("attendanceId") Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}