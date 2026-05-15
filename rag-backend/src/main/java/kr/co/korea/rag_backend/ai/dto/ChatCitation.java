package kr.co.korea.rag_backend.ai.dto;

public record ChatCitation(
		String documentId,	// 근거 문서 식별자
		String fileName,	// 근거 문서 파일명
		int chunkIndex,		// 근거 청크 번호
		Double score		// 별도 유사도 점수가 없으므로
) {
}
