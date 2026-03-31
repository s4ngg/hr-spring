package kr.co.hr.findpassword.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "인증번호 전송 요청 DTO")
@Getter
@NoArgsConstructor
public class SendCodeRequestDto {

    @Schema(description = "이메일", example = "example@nexuspro.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;
}