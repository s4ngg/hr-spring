package kr.co.korea.rag_backend.ai.dto;

import jakarta.validation.constraints.NotBlank;

public record SummaryRequest(
		@NotBlank(message = "documentId는 필수입니다.")
		String documentId, // 요약할 문서 식별자
		@NotBlank(message = "prompt는 필수입니다.")
		String prompt		// 요약 방식에 대한 사용자 선택 프롬프트
		
) {
}
