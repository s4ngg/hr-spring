package kr.co.hr.login.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.global.config.JwtProvider;
import kr.co.hr.global.config.JwtUserInfoDTO;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.dto.LoginResponseDTO;
import kr.co.hr.login.service.LoginService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
	
	private static final Logger logger = LogManager.getLogger(LoginServiceImpl.class);

	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    
    
    
    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO authenticate(LoginRequestDTO dto) {
    	
    	
        // 1. 이메일 또는 사번 으로 통합 검색
        Member member = memberRepository.findByEmailOrEmployeeNo(dto.getLoginId(), dto.getLoginId())
        		.orElseThrow(() -> {
                    logger.warn("로그인 실패 - 존재하지 않는 계정: {}", dto.getLoginId());
                    return new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
                });
        
        // 2. 비밀번호 검증 
        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
        	logger.warn("로그인 실패 - 비밀번호 불일치: {}", dto.getLoginId());
        	throw new RuntimeException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }
        
        
        JwtUserInfoDTO jwtUserDTO = JwtUserInfoDTO.builder()
        		.memberId(member.getMemberId())
                .loginId(member.getEmployeeNo())
                .name(member.getName())
                .role(member.getRole()) 
                .build();
        
        logger.info("로그인 성공 - 사번: {}, 이름: {}", member.getEmployeeNo(), member.getName());
        String token = jwtProvider.createToken(jwtUserDTO);

        return LoginResponseDTO.builder()
                .memberId(member.getMemberId())     
                .employeeNo(member.getEmployeeNo())  
                .name(member.getName())         
                .hireDate(member.getHireDate())
                // role 과 departmentId 부분은 추후 Optional을 사용하여 코드를 깔끔하게 하면 좋음 
                .role(member.getRole()) 
                .departmentId(member.getDepartment() != null ? member.getDepartment().getDepartmentId() : null) // 부서번호
                .token(token)                     
     
                .build();
        
    }
}

