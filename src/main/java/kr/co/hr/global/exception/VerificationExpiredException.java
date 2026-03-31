package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class VerificationExpiredException extends BusinessException {
    public VerificationExpiredException() {
        super("인증번호가 만료되었거나 요청하지 않은 이메일입니다.", HttpStatus.BAD_REQUEST);
    }
}