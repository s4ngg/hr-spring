package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class VerificationNotCompletedException extends BusinessException {
    public VerificationNotCompletedException() {
        super("인증이 완료되지 않았습니다. 인증번호를 다시 확인해주세요.", HttpStatus.BAD_REQUEST);
    }
}