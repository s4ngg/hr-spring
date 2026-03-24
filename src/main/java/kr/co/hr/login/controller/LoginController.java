package kr.co.hr.login.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.service.LoginService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api") 
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;
	
	@PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO dto) {
        return loginService.authenticate(dto);
    }
}
