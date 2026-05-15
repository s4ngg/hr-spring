package kr.co.korea.rag_backend.global.exception;

public class RedisUnavailableException extends RuntimeException {
	
	public RedisUnavailableException(String message, Throwable cause) {
		super(message, cause);
	}

}
