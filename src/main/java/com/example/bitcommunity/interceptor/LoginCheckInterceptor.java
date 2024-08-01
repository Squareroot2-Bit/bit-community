package com.example.bitcommunity.interceptor;

//import com.alibaba.fastjson2.JSONObject;
//import com.fasterxml.jackson.databind.util.JSONPObject;
import com.example.bitcommunity.json.Result;
import com.example.bitcommunity.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    private static ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求的url
        String url = request.getRequestURI();
        log.info("请求的url：{}", url);
        if(url.contains("login") || url.contains("regsiter")) {
            return true;
        }
        // 获取请求头中的jwt
        String jwt = request.getHeader("token");
        if(jwt == null || jwt.isEmpty()) {
            log.info("未获取到请求头中的jwt");
            Result error = Result.error("没有登录");
            response.getWriter().write(objectMapper.writeValueAsString(error));
            return false;
        }
        try {
            JwtUtil.parseJWT(jwt);
        } catch (Exception e) {
            log.info("jwt解析失败");
            Result error = Result.error("没有登录");
            response.getWriter().write(objectMapper.writeValueAsString(error));
            return false;
        }
        return true;
    }
}
