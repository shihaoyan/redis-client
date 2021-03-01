package com.shy.redis.client.dao.redis;

import com.shy.redis.client.model.request.RedisLoginParam;
import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-03-01 10:58
 */
@Component
public class RedisConnectionDao {


    public RedisConnectionFactory connect(RedisStandaloneConfiguration redisConfiguration) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();
        RedisProperties.Lettuce lettuce = new RedisProperties.Lettuce();
        builder.shutdownTimeout(lettuce.getShutdownTimeout());
        builder.clientOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.enabled()).build());
        DefaultClientResources clientResources = DefaultClientResources.create();
        builder.clientResources(clientResources);
        LettuceClientConfiguration clientConfiguration = builder.build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration,
                clientConfiguration);
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    public RedisStandaloneConfiguration getRedisConfiguration(RedisLoginParam redisLoginParam) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisLoginParam.getHost());
        config.setPort(redisLoginParam.getPort());
        config.setDatabase(redisLoginParam.getDatabase() == null ? 0 : redisLoginParam.getDatabase());
        config.setPassword(redisLoginParam.getPassword());
        return config;
    }
}
