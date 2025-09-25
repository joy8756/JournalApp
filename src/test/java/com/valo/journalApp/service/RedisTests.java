package com.valo.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTests {
    @Autowired
    private RedisTemplate getRedisConfig; // same name as defined in RedisConfig.java

    @Disabled
    @Test
    public void testRedis() {
        getRedisConfig.opsForValue().set("email", "gmail123@gmail.com");
        Object email = getRedisConfig.opsForValue().get("email");
        int a=1;
    }
}
