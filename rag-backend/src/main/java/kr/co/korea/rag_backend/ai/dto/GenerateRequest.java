package kr.co.korea.rag_backend.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record GenerateRequest(
	@NotBlank(message = "question은 필수입니다.")
	String question,	// 사용자 질문 또는 요약 요청 문장
	String answer		// 선택 입력값 (보조 텍스트)
) {
}
