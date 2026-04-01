package kr.co.hr.global.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kr.co.hr.global.response.ApiResponse;

@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 한번에 처리
public class GlobalExceptionHandler {

	 private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);
	 
	// RuntimeException 대신 BusinessException으로 교체
	 @ExceptionHandler(BusinessException.class)
	 public ResponseEntity<ApiResponse<?>> handleBusinessException(BusinessException e) {
	     ErrorCode errorCode = e.getErrorCode();
	     logger.warn("비즈니스 예외 발생 - 코드: {}, 상태: {}, 메시지: {}",
	    		    errorCode.name(),
	    		    errorCode.getStatus(),
	    		    errorCode.getMessage());	
	     return ApiResponse.fail(errorCode.getMessage(), errorCode.getStatus());
	 }

	// 유효성 검사 실패 처리 (@Valid 에서 걸린 경우) -> @Valid 는 말이 안되는 값도 DB에 저장되기 때문에 서버가 거를 수 있도록 필터링 하는  어노테이션
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 public ResponseEntity<ApiResponse<?>> handleValidException(MethodArgumentNotValidException e) {
	     String errorMessage = e.getBindingResult()
	             .getFieldErrors()
	             .stream().map(FieldError::getDefaultMessage)
	             .findFirst()
	             .orElse("유효성 검사 실패");
	     logger.warn("유효성 검사 실패 - 상태: {}, 메시지: {}", HttpStatus.BAD_REQUEST, errorMessage);
	     return ApiResponse.fail(errorMessage, HttpStatus.BAD_REQUEST);
	 }
}
