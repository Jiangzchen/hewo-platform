package org.hewo.infrastructure.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

public class JwtUtil {
    /**
     * 两个常量： 过期时间；秘钥
     */
    public static final long EXPIRE = 8640000L;
    public static final String SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    public static String sign(Long userId,String userCode){
        return Jwts.builder()
                //JWT头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                //设置分类；设置过期时间 一个当前时间，一个加上设置的过期时间常量
                .setSubject("hewo-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置token主体信息，存储用户信息
                .claim("userId", userId)
                .claim("userCode", userCode)
                //.signWith(SignatureAlgorithm.ES256, SECRET)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static boolean verify(String jwtStr){
        if (StringUtils.isEmpty(jwtStr)) {
            return false;
        }
        try{
            //验证token
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtStr);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getUserId(String jwtStr){
        if (StringUtils.isEmpty(jwtStr)){
            return "";
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtStr);
        Claims body = claimsJws.getBody();
        return (String) body.get("userId");
    }
}
