package kr.co.hr.itcontact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;
import kr.co.hr.itcontact.service.ItContactService;
import lombok.RequiredArgsConstructor;

@Tag(name = "ItContact", description = "IT 문의 API")
@RestController
@RequestMapping("/api/it-contact")
@RequiredArgsConstructor
public class ItContactController {

    private final ItContactService itContactService;

    @Operation(summary = "IT 문의 접수", description = "IT 지원팀에 문의를 접수합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createContact(
            @RequestBody @Valid ItContactRequestDto dto) {
        itContactService.createContact(dto);
        return ApiResponse.success("문의가 접수되었습니다.");
    }

    @Operation(summary = "IT 문의 목록 조회", description = "접수된 IT 문의 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ItContactResponseDto>>> getContactList() {
    	return ApiResponse.success("문의 목록 조회 성공", itContactService.getContactList());
    }
}