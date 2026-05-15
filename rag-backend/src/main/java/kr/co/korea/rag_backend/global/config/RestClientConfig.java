package kr.co.korea.rag_backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

	@Bean
	public RestClient fastApiRestClient(AiProperties properties) {
		
		//HTTP 요청 설정 객체
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		
		// 서버 연결 타임 아웃 설정
		//  - 지정한 시간 안에 연결되지 않으면 예외 발생
		
		requestFactory.setConnectTimeout(properties.getFastApi().getConnectTimeoutMillis());
		
		// 서버 응답 대기 타임 아웃 설정
		//	- 서버 연결은 성공했는데, 응답이 너무 오래 걸리면 타임 아웃
		// 	- LLM 응답 생성이 너무 길어져도 타임 아웃 발생
		
		requestFactory.setReadTimeout(properties.getFastApi().getReadTimeoutMillis());
		
		
		
		return RestClient.builder()
				.baseUrl(properties.getFastApi().getBaseUrl())
				.requestFactory(requestFactory)
				.build();
	}
}
