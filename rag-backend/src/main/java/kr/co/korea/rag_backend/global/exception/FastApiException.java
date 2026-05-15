package kr.co.korea.rag_backend.global.exception;

public class FastApiException extends RuntimeException {
	
	private final int statusCode;
	
	public FastApiException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

}
