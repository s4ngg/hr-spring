package kr.co.hr.vacation.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.config.JwtProvider;
import kr.co.hr.global.response.ApiResponse; // 배운 위치대로 임포트
import kr.co.hr.vacation.dto.VacationRequestDTO;
import kr.co.hr.vacation.dto.VacationResponseDTO;
import kr.co.hr.vacation.service.VacationService;
import lombok.RequiredArgsConstructor;

@Tag(name = "휴가 관리", description = "휴가 관리 API")
@RestController
@RequestMapping("/api/vacations")
@RequiredArgsConstructor
public class VacationController {

    private final VacationService vacationService;
    private final JwtProvider jwtProvider;

    // 1. 휴가 신청
    @Operation(summary = "휴가 신청", description = "새로운 휴가를 신청합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> requestVacation(
    		@RequestHeader("Authorization") String token, // 토큰 수신
            @RequestBody @Valid VacationRequestDTO dto) {
    	
    	// 1. 토큰에서 Bearer 제거 및 memberId(PK) 추출
        String jwt = token.substring(7);
        Long loginMemberId = jwtProvider.getLoginIdFromToken(jwt);
        

        dto.setMemberId(loginMemberId);

        vacationService.requestVacation(dto);
        return ApiResponse.success("휴가 신청 성공");
    }

    // 2. 내 휴가 내역 조회
    @Operation(summary = "내 휴가 내역 조회", description = "특정 직원의 휴가 신청 내역을 조회합니다.")
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<VacationResponseDTO>>> getMyVacationHistory(
    		@RequestHeader("Authorization") String token) {
    	
    	// 1. Bearer 제거 및 PK 추출
    	String jwt = token.substring(7);
    	Long memberId = jwtProvider.getLoginIdFromToken(jwt);
    	
    	// 2. 서비스 호출 (타입 Long)
    	List<VacationResponseDTO> list = vacationService.getMyVacationHistory(memberId);
    	
    	return ApiResponse.success("내 휴가 내역 조회 성공", list);
    }
}