package kr.co.restStudy.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
	private final HttpStatus status;
	private final String result;
	
	public MemberException(String message, String result, HttpStatus status) {
		super(message);
		this.result = result;
		this.status = status;
	}
}