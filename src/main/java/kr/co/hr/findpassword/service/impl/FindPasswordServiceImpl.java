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
import kr.co.hr.global.exception.BusinessException;
import kr.co.hr.global.exception.ErrorCode;
import kr.co.hr.itcontact.MailService;
import kr.co.hr.member.entity.Member;
import kr.co.hr.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
@RequiredArgsConstructor
public class FindPasswordServiceImpl implements FindPasswordService {

	private static final Logger logger = LogManager.getLogger(FindPasswordServiceImpl.class);
	 
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final VerificationRedisRepository verificationRedisRepository;

    @Override
    public void sendVerificationCode(SendCodeRequestDto dto) {
        memberRepository.findByEmail(dto.getEmail())
        .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        String code = generateCode();
        String encodedCode = passwordEncoder.encode(code);

        verificationRedisRepository.saveCode(dto.getEmail(), encodedCode);
        mailService.sendVerificationMail(dto.getEmail(), code);
        logger.info("인증번호 발송 - email: {}", dto.getEmail());
    }

    @Override
    public void verifyCode(VerifyCodeRequestDto dto) {
        String encodedCode = verificationRedisRepository.getCode(dto.getEmail())
        		.orElseThrow(() -> new BusinessException(ErrorCode.VERIFICATION_EXPIRED));

        if (!passwordEncoder.matches(dto.getCode(), encodedCode)) {
        	logger.warn("인증번호 검증 실패 - email: {}", dto.getEmail());
        	throw new BusinessException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        // 인증 성공 후 코드 즉시 삭제 → 재사용 방지
        verificationRedisRepository.deleteCode(dto.getEmail());
        verificationRedisRepository.saveVerified(dto.getEmail());
        logger.info("인증번호 검증 성공 - email: {}", dto.getEmail());
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequestDto dto) {
        if (!verificationRedisRepository.isVerified(dto.getEmail())) {
        	 throw new BusinessException(ErrorCode.VERIFICATION_NOT_COMPLETED);
        }

        Member member = memberRepository.findByEmail(dto.getEmail())
        		.orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        member.updatePassword(passwordEncoder.encode(dto.getNewPassword()));
        verificationRedisRepository.deleteVerified(dto.getEmail());
        logger.info("비밀번호 재설정 완료 - email: {}", dto.getEmail());
        verificationRedisRepository.deleteCode(dto.getEmail());
    }

    // SecureRandom으로 예측 불가능한 인증번호 생성
    private String generateCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }
}