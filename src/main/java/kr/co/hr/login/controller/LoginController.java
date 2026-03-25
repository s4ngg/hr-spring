package kr.co.hr.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.service.LoginService;
import lombok.RequiredArgsConstructor;

@Tag(name = "Login", description = "로그인 관련 API")
@RestController
@RequestMapping("/api") 
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;
	
	@Operation(summary = "로그인", description = "사용자의 사번 or 이메일 비밀번호를 받아 로그인을 진행하고 성공시 JWT 토큰 반환.")
	@PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO dto) {
        return loginService.authenticate(dto);
    }
}
