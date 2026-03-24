package kr.co.hr.vacation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/vacations")
@RequiredArgsConstructor
@Tag(name = "Vacation Admin", description = "관리자 전용 휴가 승인/반려 API")
public class VacationAdminController {
	
	private final VacationService vacationService;
	
	@Operation(summary = "휴가 상태 변경", description = "관리자가 휴가를 승인하거나 반려합니다.")
	@PatchMapping("/{vacationId}/status")
	
	public ResponseEntity<String> updateVacationStatus(
            @PathVariable Long vacationId,
            @RequestBody VacationAdminRequestDTO dto) {
        
        vacationService.updateVacationStatus(vacationId, dto);
        return ResponseEntity.ok("처리가 완료되었습니다.");
    }
	
	
}
