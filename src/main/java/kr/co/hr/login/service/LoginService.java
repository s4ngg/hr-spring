package kr.co.hr.login.service;


import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.dto.LoginResponseDTO;

public interface LoginService {
	
	LoginResponseDTO authenticate(LoginRequestDTO dto);
}
