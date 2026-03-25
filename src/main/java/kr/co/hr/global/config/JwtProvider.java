package kr.co.hr.global.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // 생성자 주입을 위해 추가
public class JwtProvider {
		
	private final JwtProperties jwtProperties;

	private Key getSigningKey() {
	    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
	}
    
	
	public String createToken(String loginId, String name) {
        Date now = new Date();
	
    // 만료 시간 (1시간)
    long expireTime = jwtProperties.getExpirationTime();

        return Jwts.builder()
                .setSubject(loginId)
                .claim("name", name)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
