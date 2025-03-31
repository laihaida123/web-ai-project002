package org.itheima;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {
    /**
     * 生成Jwt令牌
     */
    @Test
    public void testGenerateJwt(){
        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("id", 1);
        dataMap.put("username", "admin");
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256, "lovesinglovesinglovesinglovesinglovesinglovesing")
                .addClaims(dataMap)//添加自定义信息
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))
                .compact();//生成令牌

        System.out.println(jwt);
    }
    /**
     * 解析Jwt令牌
     */
    @Test
    public void testParseJWT(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwidXNlcm5hbWUiOiJhZG1pbiIsImV4cCI6MTc0MzM5MjE2Nn0.Q9pjeiwoFSllNKH1vNp5Rq6gZKcgByBvcng6FSGSlTI";
        Claims claims = Jwts.parser().setSigningKey("lovesinglovesinglovesinglovesinglovesinglovesing")//指定密钥
                .parseClaimsJws(token)//解析令牌
                .getBody();//获得自定义信息
        System.out.println(claims);
    }
}
