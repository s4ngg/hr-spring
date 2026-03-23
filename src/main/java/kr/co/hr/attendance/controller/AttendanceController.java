package kr.co.hr.attendance.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.co.hr.attendance.dto.AttendanceRequestDTO;
import kr.co.hr.attendance.dto.AttendanceResponseDTO;
import kr.co.hr.attendance.service.AttendanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 전체 근태 조회
    @GetMapping
    public ResponseEntity<List<AttendanceResponseDTO>> getAllAttendances() {
        return ResponseEntity.ok(attendanceService.getAllAttendances());
    }

    // 특정 직원 근태 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<List<AttendanceResponseDTO>> getAttendancesByMember(
            @PathVariable Long memberId) {
        return ResponseEntity.ok(attendanceService.getAttendancesByMember(memberId));
    }

    // 출근 체크인
    @PostMapping("/check-in")
    public ResponseEntity<AttendanceResponseDTO> checkIn(
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ResponseEntity.ok(attendanceService.checkIn(requestDTO));
    }

    // 퇴근 체크아웃
    @PutMapping("/check-out/{attendanceId}")
    public ResponseEntity<AttendanceResponseDTO> checkOut(
            @PathVariable Long attendanceId,
            @RequestBody AttendanceRequestDTO requestDTO) {
        return ResponseEntity.ok(attendanceService.checkOut(attendanceId, requestDTO));
    }

    // 근태 삭제
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long attendanceId) {
        attendanceService.deleteAttendance(attendanceId);
        return ResponseEntity.noContent().build();
    }
}