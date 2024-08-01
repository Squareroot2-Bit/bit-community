package com.example.bitcommunity.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private static String signKey = "bitbitbit";
    private static int expire = 3600*1000*24;

//    @Value("${config.jwt.signKey}")
//    public void setSignKey(String signKey) {
//        this.signKey = signKey;
//    }
//
//    @Value("${config.jwt.expire}")
//    public void setExpire(String expire) {
//        this.signKey = expire;
//    }

    // 生成jwt令牌
    public static String generateJwt(Map<String, Object> claims) {
        String jwt = Jwts.builder().addClaims(claims).signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire)).compact();
        return jwt;
    }

    // 解析jwt令牌
    public static Claims parseJWT(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(signKey).parseClaimsJws(jwt).getBody();
        return claims;
    }

    /**
     * 验证JWT Token是否有效
     * @param token JWT Token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseJWT(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
