package oort.cloud.openmarket.auth.repository;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class RefreshTokenRedisRepository {
    private final StringRedisTemplate redisTemplate;
    // 키 설계 <도메인> :: <식별자> :: <속성>
    private final static String TOKEN_KEY_FORMAT = "user::%s::refresh-token";

    public RefreshTokenRedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void createOrUpdate(Long userId, String refreshToken, Duration ttl){
        redisTemplate.opsForValue().set(generateKey(userId), refreshToken, ttl);
    }

    public void remove(Long userId){
        redisTemplate.delete(generateKey(userId));
    }

    public Optional<String> read(Long userId){
        return Optional.ofNullable(redisTemplate.opsForValue().get(generateKey(userId)));
    }

    private String generateKey(Long userId){
        return TOKEN_KEY_FORMAT.formatted(userId);
    }
}
