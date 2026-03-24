package kr.co.hr.global.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtProvider {
	// 비밀키 (최소 32자 이상)
    private final String secret = "architect-ledger-hr-project-secret-key-2026";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    
    // 만료 시간 (1시간)
    private final long expireTime = 3600000L;

    public String createToken(String loginId, String name) {
        Date now = new Date();
        
        return Jwts.builder()
                .setSubject(loginId)
                .claim("name", name)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
