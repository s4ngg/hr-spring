package kr.co.hr.member.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
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
	
	@Schema(description = "사번", example = "EMP001")
	private String employeeNo;
	
	@Schema(description = "직원 이름", example = "홍길동")
	private String name;
	
	@Schema(description = "이메일", example = "hong@company.com")
	private String email;
	
	@Schema(description = "비밀번호", example = "password123!")
	private String password;
	
	@Schema(description = "권한 (ADMIN/USER)", example = "USER")
	private String role;
	
	@Schema(description = "고용 형태 (정규직/계약직/인턴", example = "정규직")
	private String employType;
	
	@Schema(description = "입사일", example = "2024-01-01")
	private LocalDate hireDate;
	
	@Schema(description = "프로필 이미지 URL", example = "https://example.com/profile.jpg")
	private String profileImage;
	
	@Schema(description = "재직 상태 (재직/휴직/퇴직)", example = "재직")
	private String status;
}
