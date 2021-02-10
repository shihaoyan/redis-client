package com.shy.redis.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-02-09 10:59
 */
@RestController
@SuppressWarnings("all")
public class RedisClientController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 模糊匹配标识符
     */
    public static final String PATTERN = "*";

    @RequestMapping("/get/{key}")
    public Object getValue(@PathVariable("key") String key) {
        String s = redisTemplate.opsForValue().get(key);
        if (s == null) {
            return "当前key不存在";
        }
        return s;
    }

    @RequestMapping("/delete/{key}")
    public Object deleteValue(@PathVariable("key") String key) {
        Boolean delete = redisTemplate.delete(key);
        if (delete) {
            return "success";
        }
        return "失败！不存在该key";
    }

    @RequestMapping("/keys")
    public Object keys() {
        Set<String> keys = null;
        keys = redisTemplate.keys(PATTERN);
        if (keys == null || keys.size() == 0) {
            return "redis数据库中没有数据";
        }
        return keys;
    }


    @RequestMapping("/keys/{pattern}")
    public Object keysByPattern(@PathVariable("pattern") String pattern) {
        Set<String> keys;
        if (!pattern.contains(PATTERN)) {
            pattern = PATTERN + pattern + PATTERN;
        }
        keys = redisTemplate.keys(pattern);
        if (keys == null || keys.size() == 0) {
            return "redis数据库中没有数据";
        }
        return keys;
    }

    @RequestMapping("/set/{key}:{value}")
    public Object setValue(@PathVariable("key") String key, @PathVariable("value") String value) {
        redisTemplate.opsForValue().set(key, value);
        return "success";
    }


}
