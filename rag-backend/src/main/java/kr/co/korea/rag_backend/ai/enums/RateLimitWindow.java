package kr.co.korea.rag_backend.ai.enums;

import java.time.Duration;

public enum RateLimitWindow {
	SECOND("second", Duration.ofSeconds(1), "초당 요청 제한을 초과했습니다."),
	MINUTE("minute", Duration.ofMinutes(1), "분당 요청 제한을 초과했습니다."),
	DAILY("daily", Duration.ofDays(1), "일일 요청 제한을 초과했습니다.");
	
	private final String keySuffix;
	private final Duration ttl;
	private final String exceededMessage;
	
	RateLimitWindow(String keySuffix, Duration ttl, String exceededMessage) {
		this.keySuffix = keySuffix;
		this.ttl = ttl;
		this.exceededMessage = exceededMessage;
	}
	
	public String keySuffix() {
		return keySuffix;
	}
	
	public Duration ttl() {
		return ttl;
	}
	
	public String exceedeMessage() {
		return exceededMessage;
	}
}
