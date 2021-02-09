package com.shy.redis.client.controller;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
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
public class RedisClientController {

    /**
     * 模糊匹配标识符
     */
    public static final String PATTERN = "*";

    @RequestMapping("/get/{key}")
    public Object getValue(@PathVariable("key") String key) {
        StringRedisTemplate redisTemplate = getRedisTemplate();
        String s = redisTemplate.opsForValue().get(key);
        if (s == null) {
            return "当前key不存在";
        }
        return s;
    }

    @RequestMapping("/delete/{key}")
    public Object deleteValue(@PathVariable("key") String key) {
        StringRedisTemplate redisTemplate = getRedisTemplate();
        Boolean delete = redisTemplate.delete(key);
        if (delete) {
            return "success";
        }
        return "失败！不存在该key";
    }

    @RequestMapping("/keys")
    public Object keys() {
        Set<String> keys = null;
        StringRedisTemplate redisTemplate = getRedisTemplate();
        keys = redisTemplate.keys(PATTERN);
        if (keys == null || keys.size() == 0) {
            return "redis数据库中没有数据";
        }
        return keys;
    }


    @RequestMapping("/keys/{pattern}")
    public Object keysByPattern(@PathVariable("pattern") String pattern) {
        Set<String> keys;
        StringRedisTemplate redisTemplate = getRedisTemplate();
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
        StringRedisTemplate redisTemplate = getRedisTemplate();
        redisTemplate.opsForValue().set(key, value);
        return "success";
    }


    private StringRedisTemplate getRedisTemplate() {
        RedisStandaloneConfiguration config = getRedisStandaloneConfiguration();
        StringRedisTemplate redisTemplate = getStringRedisTemplate(config);
        return redisTemplate;
    }

    private RedisStandaloneConfiguration getRedisStandaloneConfiguration() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("39.105.82.150");
        config.setPort(6379);
        config.setDatabase(0);
        config.setPassword(RedisPassword.of((String) null));
        return config;
    }

    private StringRedisTemplate getStringRedisTemplate(RedisStandaloneConfiguration config) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();
        builder.shutdownTimeout(lettuce.getShutdownTimeout());
        builder.clientOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.enabled()).build());
        DefaultClientResources clientResources = DefaultClientResources.create();
        builder.clientResources(clientResources);
        LettuceClientConfiguration clientConfiguration = builder.build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(config, clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


}
