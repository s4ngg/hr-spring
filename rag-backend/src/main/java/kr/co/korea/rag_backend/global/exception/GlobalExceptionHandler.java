package kr.co.korea.rag_backend.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RateLimitException.class)
	public ResponseEntity<ApiResponse<Void>> handleRateLimit(RateLimitException exception) {
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
				.body(ApiResponse.failure("RATE_LIMIT_EXCEEDED", exception.getMessage()));
	}

	// 요청 본문 또는 헤더 검증 실패
	@ExceptionHandler({ MethodArgumentNotValidException.class, MissingRequestHeaderException.class,
			ConstraintViolationException.class, IllegalArgumentException.class })
	public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception exception) {
		return ResponseEntity.badRequest().body(ApiResponse.failure("BAD_REQUEST", exception.getMessage()));
	}

	@ExceptionHandler(FastApiException.class)
	public ResponseEntity<ApiResponse<Void>> handleFastApi(FastApiException exception) {

		// 외부 API;에 의존해야하는 상황이 있으면 별도 예외 처리를 해줘야 함.
		// - 외부 API가 커스텀 코드를 쓸 수 있기 때문에
		// (절대 안쓴다고 보장할 수 없음.)
		// - 커스텀 코드를 쓰면 HttpStatus에 존재하지 않는 코드는 null 객체로 처리되어 NPE 발생
		HttpStatus status = HttpStatus.resolve(exception.getStatusCode());

		if (status == null)
			status = HttpStatus.BAD_GATEWAY;

		return ResponseEntity.status(status).body(ApiResponse.failure("FAST_API_ERROR", exception.getMessage()));
	}

	// Redis 연결 실패
	@ExceptionHandler(RedisUnavailableException.class)
	public ResponseEntity<ApiResponse<Void>> handleRedisUnavailable(RedisUnavailableException exception) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(ApiResponse.failure("REDIS_UNAVAILABLE", exception.getMessage()));
		}

	// 예외처리할 때 Exception은 꼭 핸들링
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Void>> handleUnexcepted(Exception exception) {
		log.error("처리되지 않은 예외가 발생했습니다.", exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(ApiResponse.failure("INTERNAL_SERVER_ERROR",
						"서버 처리 중 오류가 발생했습니다."));
	}
}