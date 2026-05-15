package kr.co.korea.rag_backend.ai.service;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestClient;

import kr.co.korea.rag_backend.ai.dto.FastApiGenerateRequest;
import kr.co.korea.rag_backend.ai.dto.GenerateRequest;
import kr.co.korea.rag_backend.ai.dto.GenerateResponse;
import kr.co.korea.rag_backend.global.config.AiProperties;
import kr.co.korea.rag_backend.global.exception.FastApiException;

@Service
@RequiredArgsconstructor
public class AiGenerateService {
	
	private final RestClient fastApiRestClient;
	private final AiProperties properties;
	
	public GenerateResponse generate(String userId, GenerateRequest request) {
		FastApiGenerateRequest fastApiRequest = new FastApiGenerateRequest(
				userId,
				request.question(),
				request.answer()
		);
		
		return fastApiRestClient.post()
				.uri(properties.getFastapi().getGeneratePath())
				.body(fastApiRequest)
				.retrieve()
				.onStatus(HttpStatusCode::isError, (httpRequest, response) -> {
					String responseBody = StreamUtils.copyToString(
							response.getBody(),
							StandardCharsets.UTF_8
					);
					throw new FastApiException(
							response.getStatusCode().value(),
							responseBody.isBlank() ? "FastAPI 요청 처리에 실패했습니다." : responseBody
					);
				})
				.body(GenerateResponse.class);
	}
}
