package kr.co.korea.rag_backend.ai.cost;

import java.math.BigDecimal;

public record CostUsage(
		int inputTokens,		// 입력 토큰 수
		int outputTokens,		// 출력 토큰 수
		int totalTokens,		// 전체 토큰 수
		BigDecimal totalCostUsd	// 이번 요청의 총 비용(USD)
) {
}
