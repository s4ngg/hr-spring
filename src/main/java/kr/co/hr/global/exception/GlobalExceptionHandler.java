package kr.co.hr.global.exception;

import kr.co.hr.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 한번에 처리
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	// RuntimeException이 발생하면 이 메서드 실행한다는 뜻.	
	public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST) // Http 상태코드 400
				.body(ApiResponse.fail(e.getMessage()));
				// e.getMessage = "에러 메세지"
	}

	// 유효성 검사 실패 처리 (@Valid 에서 걸린 경우) -> @Valid 는 말이 안되는 값도 DB에 저장되기 때문에 서버가 거를 수 있도록 필터링 하는  어노테이션
	@ExceptionHandler(MethodArgumentNotValidException.class)
	//@Valid에서 걸리면 이 메서드 실행
	public ResponseEntity<ApiResponse<?>> handleValidException(MethodArgumentNotValidException e) {
		// 어떤 필드에서 어떤 에러가 났는지 메시지 추출
		String errorMessage = e.getBindingResult()
				.getFieldErrors() // 어떤 필드에서 에러났는지 가져오기
				.stream().map(FieldError::getDefaultMessage) // 에러 메세지만 가져오기
				// → "@NotBlank(message = "부서명은 필수입니다.")" 에서 message 부분
				.findFirst() // 첫번째 에러만 가져오기
				.orElse("유효성 검사 실패");

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ApiResponse.fail(errorMessage));
	}
}
