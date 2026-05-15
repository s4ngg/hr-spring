package kr.co.korea.rag_backend.ai.rate;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import kr.co.korea.rag_backend.ai.enums.RateLimitWindow;
import kr.co.korea.rag_backend.global.config.AiProperties;
import kr.co.korea.rag_backend.global.exception.RedisUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {
	
	private static final String REDIS_KEY_FORMAT = "rate:%s:%s";
	
	private final StringRedisTemplate redisTemplate;
	private final AiProperties properties;
	private final Map<String, LocalRateBucket> localBuckets = new ConcurrentHashMap<>();
	
	
	public void validate(String userId) {
		for (RateLimitWindow window : RateLimitWindow.values()) {
			checkLimit(window, userId);
		}
	}
	public void checkLimit(RateLimitWindow window, String userId) {
		String key = key(userId, window);
		long limit = limit(window);
		
		try {
			checkRedisLimit(key, limit, window.ttl(), window.exceededMessage());
		} catch (RedisConnectionFailureException exception) {
			if (!properties.getRateLimit().isLocalFallbackEnabled()) {
				throw new RedisUnavailableException("Redis 연결 실패로 Rate Limit을 확인할 수 없습니다", exception);
			}
			log.warn("Redis 연결 실패로 로컬 메모리 Rate Limit을 사용합니다. key={}", key);
			checkLocalLimit(key, limit, window.ttl(), window.exceededMessage());
		}
		
		public void checkRedisLimit(String key, long limit, Duration ttl, String message) {
			Long count = redisTemplate.opsForValue().increment(key);
			
			// INCR 결과가 1이면 새 시간 창이 시작된 것이므로 TTL을 부여한다.
			if(count != null && count == 1L) {
				redisTemplate.expire(key, ttl);
			}
			
			if(count != null && count > limit) {
				throw new RateLimitExceededException(message);
			}
		}
		
		public void checkLocalLimit(String key, long limit, Duration ttl, String message) {
			LocalRateBucket bucket = localBuckets.compute(key, (ignored, current) -> {
				Instant now = Instant.now();
				
				// 만료된 bucket이면 새 시간 창을 시작한다.
				if (current == null || now.isAfter(current.expiresAt))) {
					return new LocalRateBucket(1L, now.plus(ttl));
			}
			
			return new LocalRateBucket(current.count() + 1L, current.expiresAt());
			});
			
			if(bucket != null && bucket.count() > limit) {
				throw new RateLimitExceededException(message);
			}
		}
		
		public String key(String userId, RateLimitWindow window) {
			return REDIS_KEY_FORMAT.formatted(userId, window.keySuffix());
		}
		
		private long limit(RateLimitWindow winodw) {
			return switch (window) {
			case SECOND -> properties.getRateLimit().getSecondLimit();
			case MINUTE -> properties.getRateLimit().getMinuteLimit();
			case DAILY -> properties.getRateLimit().getDailyLimit();
			};
		}
		
	public record LocalRateBucket(long count, Instant expiresAt) {
		
	}
}
