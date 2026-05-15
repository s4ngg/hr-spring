package kr.co.korea.rag_backend.ai.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public record ChatRequest(
		
	String conversationId,		// 대화 식별자
	@NotBlank(message = "message는 필수입니다.")
	String message,				// 사용자 질문
	List<String> history,		// 이전 대화 내용
	Map<String, Object> metadata //메타데이터
) {
}
