package com.shy.redis.client.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-02-09 9:51
 */
@Configuration
public class CustomerRedisConfig {

    public static final String HOST = "localhost";
    public static final int PORT = 6379;
    public static final int INDEX = 0;
    public static final String PASSWORD = null;

    @Bean
    public RedisStandaloneConfiguration redisConfiguration() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(HOST);
        config.setPort(PORT);
        config.setDatabase(INDEX);
        config.setPassword(RedisPassword.of(PASSWORD));
        return config;
    }

    @Bean
    public StringRedisTemplate redisTemplate(RedisStandaloneConfiguration redisConfiguration) {
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
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
