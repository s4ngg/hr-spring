package kr.co.hr.member.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDTO {

	@Schema(description = "부서 ID", example = "1")
	private Long departmentId;
	
	@NotBlank(message = "사번은 필수입니다.")
	@Schema(description = "사번", example = "EMP001")
	private String employeeNo;
	
	@NotBlank(message = "이름은 필수입니다.")
	@Schema(description = "직원 이름", example = "홍길동")
	private String name;
	
	@NotBlank(message = "이메일은 필수입니다.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	@Schema(description = "이메일", example = "hong@company.com")
	private String email;
	
	@NotBlank(message = "비밀번호는 필수입니다.")
	@Size(min = 8, max = 20, message = "비밀번호는 8~20자여야 합니다.")
	@Pattern(
		    regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
		    message = "비밀번호는 영문 + 숫자 + 특수문자를 포함해야 합니다."
		)
	@Schema(description = "비밀번호", example = "password123!")
	private String password;
	
	@NotBlank(message = "권한은 필수입니다.")
	@Schema(description = "권한 (ADMIN/USER)", example = "USER")
	private String role;
	
	@NotBlank(message = "고용 형태는 필수입니다.")
	@Schema(description = "고용 형태 (정규직/계약직/인턴", example = "정규직")
	private String employType;
	
	@NotNull(message = "입사일은 필수입니다.")
	@Schema(description = "입사일", example = "2024-01-01")
	private LocalDate hireDate;
	
	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImage;
	
	@NotBlank(message = "재직 상태는 필수입니다.")
	@Schema(description = "재직 상태 (재직/휴직/퇴직)", example = "재직")
	private String status;
}
