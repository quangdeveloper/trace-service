package com.vnpts.tracebility_v2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisInstance {

    @Autowired
    private StringRedisTemplate template;

    public void set(String key, String value) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        ops.set(key, value);
    }

    public String get(String key) {
        ValueOperations<String, String> ops = this.template.opsForValue();
        return ops.get(key);
    }

    public boolean has(String key) {
        return this.template.hasKey(key);
    }
}
