package com.example.securitydemo.util;

import com.alibaba.fastjson.JSONObject;
import com.example.securitydemo.bean.vo.system.SysUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final long EXPIRATION = 1000 * 60 * 60*4; // 4小时
    private static final long EXPIRATION_REMEMBER = 1000 * 60 * 60*48; // 48小时
    // 角色的key
    private static final String ROLE_CLAIMS = "role";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "Authorization";

    // 用户信息key
    private static final String USER_CLAIMS = "user";
    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";


    public static String createTonken(SysUser user, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put(ROLE_CLAIMS, user.getRole());
        map.put(USER_CLAIMS, user);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                //主体为手机号或者账号
                .setSubject(StringUtils.hasText(user.getUsername())?user.getUsername():user.getPhone())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration ))
                .compact();
    }

    /**
     * 从tokenz中获取用户名称
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    /**
     * 从token中获取用户角色
     * @param token
     * @return
     */
    public static String getUserRole(String token) {
        return (String) getTokenBody(token).get(ROLE_CLAIMS);
    }
    public static SysUser getUser(String token) {
        Object obj = getTokenBody(token).get(USER_CLAIMS);
        return JSONObject.parseObject(JSONObject.toJSONBytes(obj), SysUser.class);
    }

    /**
     * 判断是否过期
     * @param token
     * @return
     */
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }

    }


    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
    public static void main(String[] args) {
    }
}
