package org.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // 使用 Keys.secretKeyFor(SignatureAlgorithm.HS256) 生成一个足够安全的密钥
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;//十二小时
    /**
     * 生成 Jwt 令牌
     *
     * @param claims 自定义信息
     * @return Jwt 令牌
     */
    public static String generateJwt(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SECRET_KEY) // 使用生成的密钥进行签名
                .addClaims(claims) // 添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .compact(); // 生成令牌
    }

    /**
     * 解析 Jwt 令牌
     *
     * @param token Jwt 令牌
     * @return 自定义信息
     */
    public static Claims parseJwt(String token) throws Exception{
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 指定密钥
                .parseClaimsJws(token) // 解析令牌
                .getBody(); // 获得自定义信息
    }
//    public static void main(String[] args) {
//        // 示例：生成 Jwt 令牌
//        Map<String, Object> dataMap = new HashMap<>();
//        dataMap.put("id", 1);
//        dataMap.put("username", "admin");
//        String jwt = generateJwt(dataMap, 3600 * 1000);
//        System.out.println("Generated JWT: " + jwt);
//
//        // 示例：解析 Jwt 令牌
//        Claims claims = parseJwt(jwt);
//        System.out.println("Parsed Claims: " + claims);
//    }
}
