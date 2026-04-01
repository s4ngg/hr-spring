package kr.co.hr.login.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.hr.global.config.JwtProvider;
import kr.co.hr.login.dto.LoginRequestDTO;
import kr.co.hr.login.dto.LoginResponseDTO;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtProvider jwtProvider;

    @InjectMocks
    LoginServiceImpl loginService;

    @Test
    @DisplayName("로그인 성공")
    void 로그인_성공() {
        // given
        LoginRequestDTO dto = new LoginRequestDTO("EMP001", "password123!");

        Member mockMember = Member.builder()
                .memberId(1L)
                .employeeNo("EMP001")
                .name("홍길동")
                .password("encodedPassword")
                .role("USER")
                .build();

        when(memberRepository.findByEmailOrEmployeeNo("EMP001", "EMP001"))
                .thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches("password123!", "encodedPassword"))
                .thenReturn(true);
        when(jwtProvider.createToken(ArgumentMatchers.any()))
                .thenReturn("mocked.jwt.token");

        // when
        LoginResponseDTO result = loginService.authenticate(dto);

        // then
        assertThat(result.getEmployeeNo()).isEqualTo("EMP001");
        assertThat(result.getName()).isEqualTo("홍길동");
        assertThat(result.getToken()).isEqualTo("mocked.jwt.token");
    }

    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 계정")
    void 로그인_실패_존재하지않는계정() {
        // given
        LoginRequestDTO dto = new LoginRequestDTO("없는계정", "password123!");

        when(memberRepository.findByEmailOrEmployeeNo("없는계정", "없는계정"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> loginService.authenticate(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void 로그인_실패_비밀번호불일치() {
        // given
        LoginRequestDTO dto = new LoginRequestDTO("EMP001", "wrongPassword!");

        Member mockMember = Member.builder()
                .memberId(1L)
                .employeeNo("EMP001")
                .name("홍길동")
                .password("encodedPassword")
                .role("USER")
                .build();

        when(memberRepository.findByEmailOrEmployeeNo("EMP001", "EMP001"))
                .thenReturn(Optional.of(mockMember));
        when(passwordEncoder.matches("wrongPassword!", "encodedPassword"))
                .thenReturn(false);

        // when & then
        assertThatThrownBy(() -> loginService.authenticate(dto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("아이디 또는 비밀번호가 올바르지 않습니다.");
    }
}