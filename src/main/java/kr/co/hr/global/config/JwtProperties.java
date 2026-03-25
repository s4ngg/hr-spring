package kr.co.hr.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt") // application.properties에서 'jwt'로 시작하는 설정을 매핑
public class JwtProperties {
	private String secret;
    private long expirationTime;
}
