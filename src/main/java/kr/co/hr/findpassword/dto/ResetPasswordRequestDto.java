package kr.co.hr.findpassword.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "비밀번호 재설정 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequestDto {

    @Schema(description = "이메일", example = "example@nexuspro.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "새 비밀번호", example = "newPassword123")
    @NotBlank(message = "새 비밀번호는 필수입니다.")
    private String newPassword;
}