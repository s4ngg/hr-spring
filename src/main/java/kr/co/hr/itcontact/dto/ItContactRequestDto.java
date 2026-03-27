package kr.co.hr.itcontact.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Schema(description = "IT 문의 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItContactRequestDto {

    @Schema(description = "이름", example = "홍길동")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "부서", example = "개발팀")
    @NotBlank(message = "부서는 필수입니다.")
    private String dept;

    @Schema(description = "연락처", example = "010-0000-0000")
    @NotBlank(message = "연락처는 필수입니다.")
    private String contact;

    @Schema(description = "이메일", example = "example@nexuspro.com")
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Schema(description = "제목", example = "시스템 오류 문의")
    @NotBlank(message = "제목은 필수입니다.")
    private String subject;

    @Schema(description = "내용", example = "상세 문의 내용")
    @NotBlank(message = "내용은 필수입니다.")
    private String message;
}