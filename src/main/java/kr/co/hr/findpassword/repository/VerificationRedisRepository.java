package kr.co.hr.findpassword.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VerificationRedisRepository {

    private final StringRedisTemplate redisTemplate;

    private static final String CODE_PREFIX = "findpw:";
    private static final String VERIFIED_SUFFIX = ":verified";
    private static final long EXPIRE_MINUTES = 3;

    private static String codeKey(String email) {
        return CODE_PREFIX + email;
    }

    private static String verifiedKey(String email) {
        return CODE_PREFIX + email + VERIFIED_SUFFIX;
    }
    
    public void saveCode(String email, String encodedCode) {
        redisTemplate.opsForValue().set(
                codeKey(email),
                encodedCode,
                EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public String getCode(String email) {
        return redisTemplate.opsForValue().get(codeKey(email));
    }

    public void saveVerified(String email) {
        redisTemplate.opsForValue().set(
                verifiedKey(email),
                email,
                EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public String getVerifiedEmail(String email) {
        return redisTemplate.opsForValue().get(verifiedKey(email));
    }

    public void deleteVerified(String email) {
        redisTemplate.delete(verifiedKey(email));
    }
}