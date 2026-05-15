package kr.co.korea.rag_backend.ai.dto;

import kr.co.korea.rag_backend.ai.cost.CostUsage;

public record GenerateResponse(
		String answer,	//AI 응답 텍스트
		String model,	//OpenAI 모델명
		CostUsage usage	//토큰 및 비용 사용량
) {

}
