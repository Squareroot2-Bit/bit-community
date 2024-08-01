package com.example.bitcommunity.service;

import com.example.bitcommunity.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName TokenMessage
 * @Description
 * @date 2023/12/20 10:47
 * @Author Squareroot_2
 */
@Slf4j
public class TokenMessage {
    public static Integer getUidByToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null) {
            if(token.split(" ")[1].isEmpty())
                return -2;
            Claims jwt = null;
            try {
                jwt = JwtUtil.parseJWT(token.split(" ")[1]);
            } catch (Exception e) {
                log.error("JWT解析异常 : {}", e.getMessage());
                return -3;
            }
            return jwt.get("uid", Integer.class);
        }
        return -2;
    }
    public static Integer getAdminIdByToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (token != null && !token.split(" ")[1].isEmpty()) {
            Claims jwt = null;
            try {
                jwt = JwtUtil.parseJWT(token.split(" ")[1]);
            } catch (Exception e) {
                log.error("JWT解析异常 : {}", e.getMessage());
                return -1;
            }
            return jwt.get("admin_id", Integer.class);
        }
        return -1;
    }
}
