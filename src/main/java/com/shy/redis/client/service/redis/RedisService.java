package com.shy.redis.client.service.redis;

import com.shy.redis.client.model.request.RedisLoginParam;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-03-01 10:54
 */
public interface RedisService {


    RedisConnectionFactory login(RedisLoginParam redisLoginParam);

}
