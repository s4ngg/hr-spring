package kr.co.hr.findpassword.service.impl;

import java.security.SecureRandom;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.hr.findpassword.dto.ResetPasswordRequestDto;
import kr.co.hr.findpassword.dto.SendCodeRequestDto;
import kr.co.hr.findpassword.dto.VerifyCodeRequestDto;
import kr.co.hr.findpassword.repository.VerificationRedisRepository;
import kr.co.hr.findpassword.service.FindPasswordService;
import kr.co.hr.itcontact.MailService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPasswordServiceImpl implements FindPasswordService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VerificationRedisRepository verificationRedisRepository;

    @Override
    public void sendVerificationCode(SendCodeRequestDto dto) {
        memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));

        String code = generateCode();
        String encodedCode = passwordEncoder.encode(code);

        verificationRedisRepository.saveCode(dto.getEmail(), encodedCode);
        mailService.sendVerificationMail(dto.getEmail(), code);
    }

    @Override
    public void verifyCode(VerifyCodeRequestDto dto) {
        String encodedCode = verificationRedisRepository.getCode(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("인증번호가 만료되었거나 요청하지 않은 이메일입니다."));

        if (!passwordEncoder.matches(dto.getCode(), encodedCode)) {
            throw new RuntimeException("인증번호가 올바르지 않습니다.");
        }

        // 인증 성공 후 코드 즉시 삭제 → 재사용 방지
        verificationRedisRepository.deleteCode(dto.getEmail());
        verificationRedisRepository.saveVerified(dto.getEmail());
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequestDto dto) {
        if (!verificationRedisRepository.isVerified(dto.getEmail())) {
            throw new RuntimeException("인증이 완료되지 않았습니다. 인증번호를 다시 확인해주세요.");
        }

        Member member = memberRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        verificationRedisRepository.deleteVerified(dto.getEmail());
        verificationRedisRepository.deleteCode(dto.getEmail()); // ← 이거 추가!
    }

    // SecureRandom으로 예측 불가능한 인증번호 생성
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }
}