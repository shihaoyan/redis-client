package com.shy.redis.client.controller.redis;

import com.shy.redis.client.model.request.RedisLoginParam;
import com.shy.redis.client.model.response.ResponseResult;
import com.shy.redis.client.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-02-09 10:59
 */
@RestController
@RequestMapping("/redis")
@Validated
@SuppressWarnings("all")
public class RedisClientController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpSession session;

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

    @RequestMapping("/login")
    public ResponseResult<Object> redisLogin(@Validated @RequestBody RedisLoginParam redisLoginParam) {
        RedisConnectionFactory login = redisService.login(redisLoginParam);
        if (login == null) {
            // 连接失败
            return ResponseResult.failed("redis host or port error");
        }

        // 把redis存入session中
        session.setAttribute(redisLoginParam.getHost() + ":" + redisLoginParam.getPort(), login);

        return ResponseResult.success("登录成功", redisLoginParam);
    }


}
