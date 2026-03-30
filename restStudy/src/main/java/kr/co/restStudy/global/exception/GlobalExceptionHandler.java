package kr.co.restStudy.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import kr.co.restStudy.exception.MemberException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MemberException.class)
	public ResponseEntity<String> handleMember(MemberException me) {

		return new ResponseEntity<String>(me.getResult(), me.getStatus());
	}
}
