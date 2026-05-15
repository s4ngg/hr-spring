package kr.co.korea.rag_backend.ai.dto;

import java.util.Map;

import jakarta.validation.constraints.NotBlank;

public record UploadDocumentRequest (
		@NotBlank(message="fileName은 필수입니다.")
		String fileName,
		
		@NotBlank(message="contentType은 필수입니다.")
		String contentType,
		
		@NotBlank(message="base64Content는 필수입니다.")
		String base64Content,
		
		Map<String, Object> metadata
) {

}
