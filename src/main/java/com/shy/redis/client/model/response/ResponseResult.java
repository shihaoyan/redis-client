package com.shy.redis.client.model.response;

import com.shy.redis.client.common.CommonConstant;
import lombok.Builder;
import lombok.Data;

/**
 * @author shihaoyan
 * @version:
 * @since 2021-03-01 10:34
 */
@Data
@Builder
public class ResponseResult<T> {

    private String message;
    private Integer code;
    private T data;

    public static ResponseResult success(String message, Integer code, Object data) {
        return ResponseResult.builder().message(message).code(code).data(data).build();
    }

    public static ResponseResult success(String message, Object data) {
        return ResponseResult.builder().message(message).code(CommonConstant.SUCCESS_CODE).data(data).build();
    }

    public static ResponseResult success(String message) {
        return ResponseResult.builder().message(message).code(CommonConstant.SUCCESS_CODE).build();
    }

    public static ResponseResult success(Object data) {
        return ResponseResult.builder().message(CommonConstant.SECCESS).code(CommonConstant.SUCCESS_CODE)
                .data(data).build();
    }

    public static ResponseResult success() {
        return ResponseResult.builder().message(CommonConstant.SECCESS).code(CommonConstant.SUCCESS_CODE).build();
    }

    public static ResponseResult failed(String message, Integer code, Object data) {
        return ResponseResult.builder().message(message).code(code).data(data).build();
    }

    public static ResponseResult failed(String message, Integer code) {
        return ResponseResult.builder().message(message).code(code).build();
    }

    public static ResponseResult failed(String message) {
        return ResponseResult.builder().message(message).code(CommonConstant.FAILED_CODE).build();
    }

    public static ResponseResult failed() {
        return ResponseResult.builder().message(CommonConstant.FAILED).code(CommonConstant.FAILED_CODE).build();
    }


}
