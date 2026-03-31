package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class MailSendException extends BusinessException {
    public MailSendException() {
        super("메일 전송에 실패했습니다. 잠시 후 다시 시도해주세요.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}