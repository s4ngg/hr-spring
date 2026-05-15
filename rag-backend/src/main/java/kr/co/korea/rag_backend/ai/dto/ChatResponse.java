package kr.co.korea.rag_backend.ai.dto;

import java.util.List;

public record ChatResponse(
		
	String conversationId,
	String answer,
	List<ChatCitation> citations,
	String model
) {
}
