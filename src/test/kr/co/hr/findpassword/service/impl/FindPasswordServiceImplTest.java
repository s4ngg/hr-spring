package kr.co.hr.findpassword.service.impl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.findpassword.repository.VerificationRedisRepository;
import kr.co.hr.global.exception.BusinessException;
import kr.co.hr.global.exception.ErrorCode;
import kr.co.hr.itcontact.MailService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class FindPasswordServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    MailService mailService;

    @Mock
    VerificationRedisRepository verificationRedisRepository;

    @InjectMocks
    FindPasswordServiceImpl findPasswordService;

    // ===================== sendVerificationCode =====================

    @Test
    @DisplayName("인증번호 발송 성공")
    void 인증번호_발송_성공() {
        // given
        SendCodeRequestDto dto = new SendCodeRequestDto("hong@company.com");

        Member mockMember = Member.builder()
                .memberId(1L)
                .email("hong@company.com")
                .name("홍길동")
                .build();

        when(memberRepository.findByEmail("hong@company.com"))
                .thenReturn(Optional.of(mockMember));
        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedCode");
        doNothing().when(mailService).sendVerificationMail(anyString(), anyString());

        // when
        findPasswordService.sendVerificationCode(dto);

        // then - 메서드가 정상 호출됐는지 검증
        verify(verificationRedisRepository).saveCode(anyString(), anyString());
        verify(mailService).sendVerificationMail(anyString(), anyString());
    }

    @Test
    @DisplayName("인증번호 발송 실패 - 존재하지 않는 이메일")
    void 인증번호_발송_실패_존재하지않는이메일() {
        // given
        SendCodeRequestDto dto = new SendCodeRequestDto("없는@email.com");

        when(memberRepository.findByEmail("없는@email.com"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> findPasswordService.sendVerificationCode(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    // ===================== verifyCode =====================

    @Test
    @DisplayName("인증번호 검증 성공")
    void 인증번호_검증_성공() {
        // given
        VerifyCodeRequestDto dto = new VerifyCodeRequestDto("hong@company.com", "123456");

        when(verificationRedisRepository.getCode("hong@company.com"))
                .thenReturn(Optional.of("encodedCode"));
        when(passwordEncoder.matches("123456", "encodedCode"))
                .thenReturn(true);

        // when
        findPasswordService.verifyCode(dto);

        // then
        verify(verificationRedisRepository).deleteCode("hong@company.com");
        verify(verificationRedisRepository).saveVerified("hong@company.com");
    }

    @Test
    @DisplayName("인증번호 검증 실패 - 만료된 인증번호")
    void 인증번호_검증_실패_만료() {
        // given
        VerifyCodeRequestDto dto = new VerifyCodeRequestDto("hong@company.com", "123456");

        when(verificationRedisRepository.getCode("hong@company.com"))
                .thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> findPasswordService.verifyCode(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.VERIFICATION_EXPIRED.getMessage());
    }

    @Test
    @DisplayName("인증번호 검증 실패 - 잘못된 인증번호")
    void 인증번호_검증_실패_잘못된코드() {
        // given
        VerifyCodeRequestDto dto = new VerifyCodeRequestDto("hong@company.com", "999999");

        when(verificationRedisRepository.getCode("hong@company.com"))
                .thenReturn(Optional.of("encodedCode"));
        when(passwordEncoder.matches("999999", "encodedCode"))
                .thenReturn(false);

        // when & then
        assertThatThrownBy(() -> findPasswordService.verifyCode(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.INVALID_VERIFICATION_CODE.getMessage());
    }

    // ===================== resetPassword =====================

    @Test
    @DisplayName("비밀번호 재설정 성공")
    void 비밀번호_재설정_성공() {
        // given
        ResetPasswordRequestDto dto = new ResetPasswordRequestDto("hong@company.com", "newPassword123!");

        Member mockMember = Member.builder()
                .memberId(1L)
                .email("hong@company.com")
                .password("oldEncodedPassword")
                .build();

        when(verificationRedisRepository.isVerified("hong@company.com"))
                .thenReturn(true);
        when(memberRepository.findByEmail("hong@company.com"))
                .thenReturn(Optional.of(mockMember));
        when(passwordEncoder.encode("newPassword123!"))
                .thenReturn("newEncodedPassword");

        // when
        findPasswordService.resetPassword(dto);

        // then
        verify(verificationRedisRepository).deleteVerified("hong@company.com");
        verify(verificationRedisRepository).deleteCode("hong@company.com");
    }

    @Test
    @DisplayName("비밀번호 재설정 실패 - 인증 미완료")
    void 비밀번호_재설정_실패_인증미완료() {
        // given
        ResetPasswordRequestDto dto = new ResetPasswordRequestDto("hong@company.com", "newPassword123!");

        when(verificationRedisRepository.isVerified("hong@company.com"))
                .thenReturn(false);

        // when & then
        assertThatThrownBy(() -> findPasswordService.resetPassword(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage(ErrorCode.VERIFICATION_NOT_COMPLETED.getMessage());
    }
}