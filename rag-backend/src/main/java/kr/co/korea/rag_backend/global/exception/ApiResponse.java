package kr.co.korea.rag_backend.global.exception;

// record
//  - DTO(응답 객체 등), 값 객체 등에 주로 사용되는 기능
//	- java 16부터 정식으로 제공하는 기능
//	- getter, equals 등을 컴파일러가 자동 생성
//	- 가장 큰 특징 : 불변 객체
public record ApiResponse<T>(
		boolean success,
		T data,
		ErrorBody error
) {
	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, data, null);
	}
	public static ApiResponse<Void> failure(String code, String message) {
		return new ApiResponse<>(false, null, new ErrorBody(code, message));
	}
	
	public record ErrorBody(String code, String message) {
		
	}
}
