package kr.co.hr.itcontact.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.itcontact.controller.docs.ItContactControllerDocs;
import kr.co.hr.itcontact.dto.ItContactRequestDto;
import kr.co.hr.itcontact.dto.ItContactResponseDto;
import kr.co.hr.itcontact.service.ItContactService;
import lombok.RequiredArgsConstructor;

@Tag(name = "ItContact", description = "IT 문의 API")
@RestController
@RequestMapping("/api/it-contact")
@RequiredArgsConstructor
public class ItContactController implements ItContactControllerDocs {

    private final ItContactService itContactService;

    @Override
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createContact(
            @RequestBody @Valid ItContactRequestDto dto) {
        itContactService.createContact(dto);
        return ApiResponse.success("문의가 접수되었습니다.");
    }

    @Override
    @GetMapping
    public ResponseEntity<ApiResponse<List<ItContactResponseDto>>> getContactList() {
    	return ApiResponse.success("문의 목록 조회 성공", itContactService.getContactList());
    }
}