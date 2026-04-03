package kr.co.hr.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.hr.global.response.ApiResponse;
import kr.co.hr.member.dto.MemberRequestDTO;
import kr.co.hr.member.dto.MemberResponseDTO;
import kr.co.hr.member.service.MemberService;
import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/members/test")
@RequiredArgsConstructor
public class TestController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ApiResponse<MemberResponseDTO>> createMember(
            @Valid @RequestBody MemberRequestDTO requestDTO) {
        return ApiResponse.success("직원 등록 성공", memberService.createMember(requestDTO));
    }

}
