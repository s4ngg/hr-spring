package kr.co.hr.findpassword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import kr.co.hr.findpassword.controller.docs.FindPasswordControllerDocs;
import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.findpassword.service.FindPasswordService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/find-password")
@RequiredArgsConstructor
public class FindPasswordController implements FindPasswordControllerDocs {

    private final FindPasswordService findPasswordService;

    @Override
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendCode(
            @RequestBody @Valid SendCodeRequestDto dto) {
        findPasswordService.sendVerificationCode(dto);
        return ApiResponse.success("인증번호가 전송되었습니다.");

    }

    @Override
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Void>> verifyCode(
            @RequestBody @Valid VerifyCodeRequestDto dto) {
        findPasswordService.verifyCode(dto);
        return ApiResponse.success("인증번호가 확인되었습니다.");
    }

    @Override
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestBody @Valid ResetPasswordRequestDto dto) {
        findPasswordService.resetPassword(dto);
        return ApiResponse.success("비밀번호가 변경되었습니다.");
    }
}