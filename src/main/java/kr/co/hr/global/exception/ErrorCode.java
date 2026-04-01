package kr.co.hr.global.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Member
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "해당 직원이 없습니다."),

    // Department
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "DEPARTMENT_NOT_FOUND", "해당 부서가 없습니다."),
    SEARCH_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, "SEARCH_RESULT_NOT_FOUND", "검색 결과가 없습니다."),

    // Login
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED, "INVALID_LOGIN", "아이디 또는 비밀번호가 올바르지 않습니다."),

    ATTENDANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "ATTENDANCE_NOT_FOUND", "해당 근태 정보가 없습니다."),
    ALREADY_CHECKED_IN(HttpStatus.BAD_REQUEST, "ALREADY_CHECKED_IN", "이미 출근 처리되었습니다."),
    NOT_CHECKED_IN(HttpStatus.BAD_REQUEST, "NOT_CHECKED_IN", "출근 기록이 없습니다."),
    
    // FindPassword
    VERIFICATION_EXPIRED(HttpStatus.BAD_REQUEST, "VERIFICATION_EXPIRED", "인증번호가 만료되었거나 요청하지 않은 이메일입니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.BAD_REQUEST, "INVALID_VERIFICATION_CODE", "인증번호가 올바르지 않습니다."),
    VERIFICATION_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "VERIFICATION_NOT_COMPLETED", "인증이 완료되지 않았습니다. 인증번호를 다시 확인해주세요."),

    // Mail
    MAIL_SEND_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_SEND_FAIL", "메일 전송에 실패했습니다. 잠시 후 다시 시도해주세요.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}