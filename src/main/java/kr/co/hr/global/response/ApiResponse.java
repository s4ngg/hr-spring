package kr.co.hr.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> { // T로 설정하는 이유: 응답 data에 들어오는 타입이 매번 다르기 때문에 <T> 타입으로 나중에 설정한다는 뜻 그냥 이름일뿐임.
	private boolean success; // 성공 or 실패
	private String message;
	private T data;
	
	//성공(데이터 들어옴)
	public static <T> ApiResponse<T> success(String message, T data){
		return new ApiResponse<>(true, message, data);
	}
	
	//실패 (데이터 안들어옴 - post, put, delete)
	public static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(true, message, null);
	}
	
	//실패
	public static <T> ApiResponse<T> fail(String message){
		return new ApiResponse<>(false, message, null);
	}
}
