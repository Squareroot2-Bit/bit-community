package com.example.bitcommunity.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /***
     * 响应码：
     * 0 -> 成功
     * 1 -> 普通错误
     * 2 -> 未登录错误
     * 3 -> 无权限错误
     * */
    private Short code;
    // shor信息
    private String msg;
    // 响应数据
    private Object data;

    public static Result success() {
        return new Result((short) 0, "成功", null);
    }

    public static Result success(Object data) {
        return new Result((short) 0, "成功", data);
    }

    public static Result error(String msg) {
        return new Result((short) 1, msg, null);
    }
    public static Result notLogin() {
        return new Result((short) 101, "用户未登录！", null);
    }
    public static Result tokenExpired() {
        return new Result((short) 102, "用户验证已过期，请重新验证", null);
    }
}
