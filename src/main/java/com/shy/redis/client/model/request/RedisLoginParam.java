package com.shy.redis.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-03-01 10:49
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
public class RedisLoginParam {
    @NonNull
    private String host;
    @NonNull
    private Integer port;
    @Nullable
    private Integer database;
    @Nullable
    private String password;
}
