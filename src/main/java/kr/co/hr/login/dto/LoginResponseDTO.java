package kr.co.hr.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;



@Schema(description = "로그인 응답 요청 ")
@Getter
@Builder
public class LoginResponseDTO {
	
	@Schema(description = "회원 번호", example = "1")
	private Long memberId;
	
	@Schema(description = "사번 ", example = "101")
	private String employeeNo;
	
	@Schema(description = "이름", example = "병가,연차")
	private String name;
	
	@Schema(description = "직책", example = "병가,연차")
	private String role;
	
	@Schema(description = "부서 번호", example = "15")
	private String departmentId;
}
