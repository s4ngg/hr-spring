package kr.co.hr.login.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequestDTO {
	private String loginId; // 사번 or 이메일 
    private String password;
}
