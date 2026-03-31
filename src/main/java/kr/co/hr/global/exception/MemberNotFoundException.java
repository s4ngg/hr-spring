package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException() {
        super("해당 직원이 없습니다.", HttpStatus.NOT_FOUND);
    }
}