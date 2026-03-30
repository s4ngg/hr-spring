package kr.co.hr.findpassword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.findpassword.service.FindPasswordService;
import kr.co.hr.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@Tag(name = "FindPassword", description = "비밀번호 찾기 API")
@RestController
@RequestMapping("/api/find-password")
@RequiredArgsConstructor
public class FindPasswordController {

    private final FindPasswordService findPasswordService;

    @Operation(summary = "인증번호 전송", description = "입력한 이메일로 인증번호를 전송합니다.")
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<Void>> sendCode(
            @RequestBody @Valid SendCodeRequestDto dto) {
        findPasswordService.sendVerificationCode(dto);
        return ResponseEntity.ok(ApiResponse.success("인증번호가 전송되었습니다."));
    }

    @Operation(summary = "인증번호 검증", description = "입력한 인증번호를 검증합니다.")
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Void>> verifyCode(
            @RequestBody @Valid VerifyCodeRequestDto dto) {
        findPasswordService.verifyCode(dto);
        return ResponseEntity.ok(ApiResponse.success("인증번호가 확인되었습니다."));
    }

    @Operation(summary = "비밀번호 재설정", description = "새로운 비밀번호로 변경합니다.")
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestBody @Valid ResetPasswordRequestDto dto) {
        findPasswordService.resetPassword(dto);
        return ResponseEntity.ok(ApiResponse.success("비밀번호가 변경되었습니다."));
    }
}