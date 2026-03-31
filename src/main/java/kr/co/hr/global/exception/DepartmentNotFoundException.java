package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

public class DepartmentNotFoundException extends BusinessException {
    public DepartmentNotFoundException() {
        super("해당 부서가 없습니다.", HttpStatus.NOT_FOUND);
    }
}