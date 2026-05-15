package kr.co.korea.rag_backend.ai.dto;

public record FastApiGenerateRequest(
	String userId,				// 사용자 식별자
	String question,			// 사용자 질문 또는 문서
	String answer				// 문서 본문 또는 보조 텍스트
) {
}
