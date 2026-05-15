package kr.co.korea.rag_backend.ai.dto;

public record SummaryResponse(
	String documentId,	// 요약 대상 문서 식별자 
	String summary,		// 문서 요약 결과
	String model		// OpenAI 모델명
) {
}
