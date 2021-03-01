package com.shy.redis.client.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.TimeoutOptions;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
@SuppressWarnings("all")
public class CustomerRedisConfig {

    @Value("${redis.host}")
    public String host;
    @Value("${redis.port}")
    public int port;
    @Value("${redis.database}")
    public int database;
    @Value("${redis.password}")
    public String password;

    @Bean
    @Scope("session")
    public RedisStandaloneConfiguration redisConfiguration() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);
        config.setPassword(RedisPassword.of(password));
        return config;
    }

    @Bean
    @Scope("session")
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
