package kr.co.hr.login.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {
	
	@Schema(description = "사번 또는 이메일", example = "2026001")
	private String loginId; // 사번 or 이메일 
	
	@Schema(description = "비밀번호", example = "1234")
    private String password;
}
