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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.vacation.controller.docs.VacationAdminControllerDocs;
import kr.co.hr.vacation.dto.VacationAdminRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/vacations")
@RequiredArgsConstructor
@Tag(name = "관리자 휴가관리", description = "관리자 전용 휴가 승인/반려 API")
public class VacationAdminController implements VacationAdminControllerDocs {
	
	private final VacationService vacationService;
	
	  
	// 승인 대기 목록 조회
	@Override
	@GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getPendingVacations() {
        List<VacationResponseDTO> list = vacationService.getPendingVacations();
        return ApiResponse.success("승인 대기 목록 조회 성공", list);
    }
	
	
	
	// 휴가 상태 변경 
	@Override
	@PatchMapping("/{vacationId}/status")
    public ResponseEntity<ApiResponse<Void>> updateVacationStatus(
            @PathVariable("vacationId") Long vacationId,
            @RequestBody VacationAdminRequestDTO dto) {
        
        vacationService.updateVacationStatus(vacationId, dto);
        return ApiResponse.success("휴가 상태 변경 처리가 완료되었습니다.");
    }
	
	
}