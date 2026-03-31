package kr.co.hr.vacation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/vacations")
@RequiredArgsConstructor
@Tag(name = "Vacation Admin", description = "관리자 전용 휴가 승인/반려 API")
@Tag(name = "관리자 휴가관리", description = "관리자 전용 휴가 승인/반려 API")
public class VacationAdminController {
	
	private final VacationService vacationService;
	
	  
	// 승인 대기 목록 조회
    @Operation(summary = "승인 대기 목록 조회", description = "관리자가 승인해야 할 휴가 목록을 조회합니다.")
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getPendingVacations() {
        List<VacationResponseDTO> list = vacationService.getPendingVacations();
        return ApiResponse.success("승인 대기 목록 조회 성공", list);
    }
	
	
	
	// 휴가 상태 변경 
    @Operation(summary = "휴가 상태 변경", description = "관리자가 특정 휴가 신청을 승인하거나 반려합니다.")
    @PatchMapping("/{vacationId}/status")
    public ResponseEntity<ApiResponse<Void>> updateVacationStatus(
            @PathVariable("vacationId") Long vacationId,
            @RequestBody VacationAdminRequestDTO dto) {
        
        vacationService.updateVacationStatus(vacationId, dto);

        return ApiResponse.success("휴가 상태 변경 처리가 완료되었습니다.");
    }
	
	
}