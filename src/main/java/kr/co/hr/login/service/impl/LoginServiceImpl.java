package kr.co.hr.login.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.hr.global.config.JwtProvider;
import kr.co.hr.global.config.JwtUserInfoDTO;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.service.LoginService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    
    
    
    @Override
    public String authenticate(LoginRequestDTO dto) {
    	
    	
        // 1. 이메일 또는 사번 으로 통합 검색
        Member member = memberRepository.findByEmailOrEmployeeNo(dto.getLoginId(), dto.getLoginId())
                .orElseThrow(() -> new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다."));
        
        // 2. 비밀번호 검증 
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
        	throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        
        
        JwtUserInfoDTO jwtUserDTO = JwtUserInfoDTO.builder()
                .loginId(member.getEmployeeNo())
                .name(member.getName())
             //   .role(member.getRole()) // 필요시 추가
                .build();
        
        String token = jwtProvider.createToken(jwtUserDTO);

        return token;
    }
}

