package kr.co.hr.login.service;

import kr.co.hr.login.dto.LoginRequestDTO;

public interface LoginService {
	
    String authenticate(LoginRequestDTO dto);
}
