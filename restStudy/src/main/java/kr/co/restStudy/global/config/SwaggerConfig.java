package kr.co.restStudy.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info()
									.title("rest-study API 문서화")
									.version("v1")
									.description("rest-study 프로젝트에 대한 문서"));
	}
}
