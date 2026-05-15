package kr.co.korea.rag_backend.ai.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kr.co.korea.rag_backend.ai.dto.ChatCitation;
import kr.co.korea.rag_backend.ai.dto.ChatRequest;
import kr.co.korea.rag_backend.ai.dto.ChatResponse;
import kr.co.korea.rag_backend.ai.dto.GenerateRequest;
import kr.co.korea.rag_backend.ai.dto.GenerateResponse;
import kr.co.korea.rag_backend.ai.dto.SummaryRequest;
import kr.co.korea.rag_backend.ai.dto.SummaryResponse;
import kr.co.korea.rag_backend.ai.rate.RateLimitService;
import kr.co.korea.rag_backend.ai.service.AiGenerateService;
import kr.co.korea.rag_backend.ai.service.DocumentUploadService;
import kr.co.korea.rag_backend.global.config.AiProperties;
import kr.co.korea.rag_backend.global.exception.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentAiController {

	private static final String USER_ID_HEADER = "X-User-Id";
	private static final String DOCUMENT_IDS_METADATA_KEY = "documentIds";
	private static final String HISTORY_SECTION = "[History]\n";
	private static final String DOCUMENTS_SECTION = "[Documents]\n";
	
	private final DocumentUploadService documentUploadService;
	private final RateLimitService rateLimitService;
	private final AiGenerateService aiGenerateService;
	private final AiProperties properties;
	
	@PostMapping(AiApiPaths.SUMMARY)
	public ResponseEntity<ApiResponse<SummaryResponse>> summarize(
			@RequestHeader(value = USER_ID_HEADER, required = false) String userId,
			@Valid @RequestBody SummaryRequest request
	)	{
		String resolvedUserId = resolveUserId(userId);
		rateLimitService.validate(resolvedUserId);
		
		DocumentUploadService.StoredDocument document = documentUploadService.findById(request.documentId())
				.orElseThrow(()-> new IllegalArgumentException("요약할 문서를 찾을 수 없습니다. 다시 업로드해 주세요."));
		
		String context = limitContext(document.content());
		GenerateResponse generated = aiGenerateService.generate(
				resolvedUserId,
				new GenerateRequest(request.prompt(), context)
		);
		
		SummaryResponse response = new SummaryResponse(
				request.documentId(),
				generated.answer(),
				generated.model()
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
	
	@PostMapping(AiApiPaths.CHAT)
	public ResponseEntity<ApiResponse<ChatResponse>> chat(
			@RequestHeader(value = USER_ID_HEADER, required = false) String userId,
			@Valid @RequestBody ChatRequest request
	) {
		String resolvedUserId = resolveUserId(userId);
		rateLimitService.validate(resolvedUserId);
		
		List<DocumentUploadService.StoredDocument> documents = resolveDocuments(request.metadata());
		String context = limitContext(buildChatContext(documents, request.history()));
		
		
		GenerateResponse generated = aiGenerateService.generate(
				resolvedUserId,
				new GenerateRequest(request.message(), context)
		);
		
		List<ChatCitation> citations = documents.stream()
				.map(document -> new ChatCitation(document.documentId(), document.fileName(), 0, null))
				.toList();
		
		ChatResponse response = new ChatResponse(
				request.conversationId(),
				generated.answer(),
				citations,
				generated.model()
		);
		return ResponseEntity.ok(ApiResponse.success(response));
	}
	private String resolveUserId(String userId) {
		if (userId == null || userId.isBlank()) {
			return properties.getUser().getDefaultId();
		}
		return userId;
	}
	
	private List<DocumentUploadService.StoredDocument> resolveDocuments(Map<String, Object> metadata) {
		if (metadata == null || !(metadata.get(DOCUMENT_IDS_METADATA_KEY) instanceof List<?> documentIds)) {
			return List.of();
		}
		
		List<DocumentUploadService.StoredDocument> documents = new ArrayList<>();
		for (Object value : documentIds) {
			if (value instanceof String documentId) {
				documentUploadService.findById(documentId).ifPresent(documents::add);
			}
		}
		return documents;
	}
	
	private String buildChatContext(List<DocumentUploadService.StoredDocument> documents, List<String> history) {
		StringBuilder context = new StringBuilder();
		
		if(history != null && !history.isEmpty()) {
			context.append(HISTORY_SECTION);
			history.forEach(item -> context.append(item).append('\n'));
			context.append('\n');
		}
		
		if (!documents.isEmpty()) {
			context.append(DOCUMENTS_SECTION);
			for (DocumentUploadService.StoredDocument document : documents) {
				context.append("fileName: ").append(document.fileName()).append('\n');
				context.append(document.content()).append("\n\n");
			}
		}
		
		return context.toString();
	}
	
	private String limitContext(String context) {
		int maxContextCharacters = properties.getDocument().getMaxContextCharacters();
		if (context == null || context.length() <= maxContextCharacters) {
			return context == null ? "" : context;
		}
		return context.substring(0, maxContextCharacters);
	}
}
