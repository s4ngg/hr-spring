package kr.co.hr.findpassword.repository;

import java.util.Optional;
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

    // 정적 메서드 - Key 생성
    private static String codeKey(String email) {
        return CODE_PREFIX + email;
    }

    private static String verifiedKey(String email) {
        return CODE_PREFIX + email + VERIFIED_SUFFIX;
    }

    // 인증번호 저장
    public void saveCode(String email, String encodedCode) {
        redisTemplate.opsForValue().set(
                codeKey(email),
                encodedCode,
                EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );
    }

    // 인증번호 조회 - Optional로 null-safe 처리
    public Optional<String> getCode(String email) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(codeKey(email)));
    }

    // 인증번호 삭제
    public void deleteCode(String email) {
        redisTemplate.delete(codeKey(email));
    }

    // 인증 완료 표시 저장 - boolean(true)으로 저장
    public void saveVerified(String email) {
        redisTemplate.opsForValue().set(
                verifiedKey(email),
                "true",
                EXPIRE_MINUTES,
                TimeUnit.MINUTES
        );
    }

    // 인증 완료 여부 확인
    public boolean isVerified(String email) {
        return Boolean.parseBoolean(redisTemplate.opsForValue().get(verifiedKey(email)));
    }

    // 인증 완료 정보 + 혹시 남은 코드 삭제
    public void deleteVerified(String email) {
        redisTemplate.delete(verifiedKey(email));
        redisTemplate.delete(codeKey(email));
    }
}