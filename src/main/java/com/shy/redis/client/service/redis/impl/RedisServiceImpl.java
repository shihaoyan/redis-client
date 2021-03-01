package com.shy.redis.client.service.redis.impl;

import com.shy.redis.client.dao.redis.RedisConnectionDao;
import com.shy.redis.client.model.request.RedisLoginParam;
import com.shy.redis.client.model.response.ResponseResult;
import com.shy.redis.client.service.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.stereotype.Service;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-03-01 10:56
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisConnectionDao redisConnectionDao;

    @Override
    public RedisConnectionFactory login(RedisLoginParam redisLoginParam) {
        RedisStandaloneConfiguration redisConfiguration = redisConnectionDao.getRedisConfiguration(redisLoginParam);
        RedisConnectionFactory connect = null;
        try {
            // 得到redis连接工厂
            connect = redisConnectionDao.connect(redisConfiguration);
            log.info("redis connection success");
        } catch (Exception e) {
            log.error("Redis connection failed", e);
        }
        return connect;
    }
}
