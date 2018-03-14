package com.redis.redis.service.impl;

import com.redis.redis.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String USER_TOKEN = "USER_TOKEN_";

    private  Long MAX_SAVE_TIME=7L;

    private String USER_SESSION = "USER_SESSION_";

    @Override
    public void saveUserToken(Long userId, String loginInfo) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String userToken = UUID.randomUUID().toString();
        ops.set(USER_TOKEN + userId, userToken,MAX_SAVE_TIME, TimeUnit.DAYS);
        log.info(USER_TOKEN + userId,userToken);
        ops.set(USER_SESSION + userToken, loginInfo,MAX_SAVE_TIME, TimeUnit.DAYS);
    }

    @Override
    public String getUserToken(Long userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (!redisTemplate.hasKey(USER_TOKEN + userId)) {
            return null;
        }
        String userToken = ops.get(USER_TOKEN + userId);
        if (!redisTemplate.hasKey(USER_SESSION + userToken)) {
            return null;
        }
        return ops.get(USER_SESSION + userToken);
    }

    @Override
    public void cleanToken(Long userId) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(USER_TOKEN + userId)) {
            String userToken = ops.get(USER_TOKEN + userId);
            redisTemplate.delete(USER_TOKEN + userId);
            if (redisTemplate.hasKey(USER_SESSION + userToken)) {
                redisTemplate.delete(USER_SESSION + userToken);
            }
        }
    }
}
