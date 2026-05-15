package kr.co.korea.rag_backend.ai.dto;

import java.util.Map;

public record UploadDocumentResponse(
		String documentId, String fileName, String contentType, int chunkCount, String preview,
		Map<String, Object> metadata
		
		) {
}
