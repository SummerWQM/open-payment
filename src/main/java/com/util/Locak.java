package com.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class Locak {

    @Autowired
    RedisTemplate redisTemplate;

    public boolean lock(String key, int value, long releaseTime) {
        // 尝试获取锁
        Boolean boo = redisTemplate.opsForValue().setIfAbsent(key, value, releaseTime, TimeUnit.SECONDS);
        // 判断结果
        return boo != null && boo;
    }

    public void deleteLock(String key) {
        redisTemplate.delete(key);
    }
}
