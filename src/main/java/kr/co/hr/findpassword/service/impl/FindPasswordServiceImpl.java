package kr.co.hr.findpassword.service.impl;

import java.util.Random;

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
        String encodedCode = verificationRedisRepository.getCode(dto.getEmail());

        if (encodedCode == null) {
            throw new RuntimeException("인증번호가 만료되었거나 요청하지 않은 이메일입니다.");
        }

        if (!passwordEncoder.matches(dto.getCode(), encodedCode)) {
            throw new RuntimeException("인증번호가 올바르지 않습니다.");
        }

        verificationRedisRepository.saveVerified(dto.getEmail());
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequestDto dto) {
        String verifiedEmail = verificationRedisRepository.getVerifiedEmail(dto.getEmail());

        if (verifiedEmail == null) {
            throw new RuntimeException("인증이 완료되지 않았습니다. 인증번호를 다시 확인해주세요.");
        }

        if (!verifiedEmail.equals(dto.getEmail())) {
            throw new RuntimeException("인증된 이메일과 요청 이메일이 일치하지 않습니다.");
        }

        Member member = memberRepository.findByEmail(verifiedEmail)
                .orElseThrow(() -> new RuntimeException("해당 이메일로 가입된 회원이 없습니다."));

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        verificationRedisRepository.deleteVerified(dto.getEmail());
    }

    private String generateCode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }
}