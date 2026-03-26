package kr.co.hr.global.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor // 생성자 주입을 위해 추가
@Getter
public class JwtProvider {
		
	private final JwtProperties jwtProperties;

	private Key getSigningKey() {
	    return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
	}
    
	
	public String createToken(JwtUserInfoDTO jwtUserDTO) {
        Date now = new Date();
        long expireTime = jwtProperties.getExpirationTime();

        return Jwts.builder()
                .setSubject(String.valueOf(jwtUserDTO.getMemberId()))
                .claim("loginId", jwtUserDTO.getLoginId())
                .claim("name", jwtUserDTO.getName())
                .claim("role", jwtUserDTO.getRole()) // 권한 정보 추가
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
	
	
	public Long getLoginIdFromToken(String token) {
		String subject = Jwts.parserBuilder()
	            .setSigningKey(getSigningKey())
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject();
		return Long.valueOf(subject);
	}
	
	

}
