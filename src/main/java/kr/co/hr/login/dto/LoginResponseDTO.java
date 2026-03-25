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
	
	@Schema(description = "사번 ", example = "20260101")
	private String employeeNo;
	
	@Schema(description = "이름", example = "이현재")
	private String name;
	
	@Schema(description = "직책", example = "MANAGER")
	private String role;
	
	@Schema(description = "부서명", example = "개발팀")
    private String deptName; 
	
	@Schema(description = "부서 번호", example = "15")
	private Long departmentId;
	
	@Schema(description = "JWT 토큰")
    private String token; // 토큰도 잊지 말고 꼭 담아주세요.
}
