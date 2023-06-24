package com.ticketing.infra.redis.util;

import java.time.Duration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisUtil {

  private static final Long DURATION = 30L;

  private final RedisTemplate<String, String> redisTemplate;

  public RedisUtil(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void setDataWithExpire(String key, String value) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    Duration expireDuration = Duration.ofMinutes(DURATION);
    valueOperations.set(key, value, expireDuration);
  }

}
