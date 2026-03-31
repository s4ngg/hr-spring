package kr.co.hr.global.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "공통 API 응답 DTO")
public class ApiResponse<T> {

	@Schema(description = "요청 성공 여부", example = "true")
	private boolean success;

	@Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
	private String message;

	@Schema(description = "응답 데이터")
	private T data;

    // 성공 (데이터 있음)
    
    public static <T> ResponseEntity<ApiResponse<T>> success(String message, T data) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, data));
    }

    // 성공 (데이터 없음)
    public static <T> ResponseEntity<ApiResponse<T>> success(String message) {
        return ResponseEntity.ok(new ApiResponse<>(true, message, null));
    }

    // 실패
    public static ResponseEntity<ApiResponse<?>> fail(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(false, message, null));
    }
}