package kr.co.hr.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.hr.login.controller.docs.LoginControllerDocs;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.dto.LoginResponseDTO;
import kr.co.hr.login.service.LoginService;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api") 
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs{
	private final LoginService loginService;
	
	@Override
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
	    // 1. 서비스에서 토큰을 받아옵니다.
	    LoginResponseDTO token = loginService.authenticate(dto);
	    
	    return ResponseEntity.ok(token);
}
}
