package kr.co.hr.findpassword.service.impl;

import java.util.Random;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.findpassword.entity.VerificationCode;
import kr.co.hr.findpassword.repository.VerificationCodeRepository;
import kr.co.hr.findpassword.service.FindPasswordService;
import kr.co.hr.itcontact.MailService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPasswordServiceImpl implements FindPasswordService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Transactional
    @Override
    public void sendVerificationCode(SendCodeRequestDto dto) {
        // 1. 이메일로 회원 존재 여부 확인
        memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));

        // 2. 6자리 인증번호 생성
        String code = generateCode();

        // 3. DB 저장
        verificationCodeRepository.save(VerificationCode.of(dto.getEmail(), code));

        // 4. 이메일 전송
        mailService.sendVerificationMail(dto.getEmail(), code);
    }

    @Transactional
    @Override
    public void verifyCode(VerifyCodeRequestDto dto) {
        // 1. 가장 최근 인증번호 조회
        VerificationCode verification = verificationCodeRepository
                .findTopByEmailOrderByExpiredAtDesc(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("인증번호를 먼저 요청해주세요."));

        // 2. 만료 여부 확인
        if (verification.isExpired()) {
            throw new RuntimeException("인증번호가 만료되었습니다. 다시 요청해주세요.");
        }

        // 3. 인증번호 일치 여부 확인
        if (!verification.getCode().equals(dto.getCode())) {
            throw new RuntimeException("인증번호가 올바르지 않습니다.");
        }
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequestDto dto) {
        // 1. 회원 조회
        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));

        // 2. 비밀번호 암호화 후 변경
        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
    }

    // 6자리 인증번호 생성 헬퍼 메서드
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}