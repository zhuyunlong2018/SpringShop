package com.bianquan.springShop.common.utils;


import io.jsonwebtoken.*;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@ConfigurationProperties(prefix = "bianquan.jwt")
public class JwtUtil {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String secret;
    private long expire;
    private String header;

    public static String HEADER_TOKEN = "token";

    /**
     * 生成token -- 商城移动端，使用用户id生成
     * @param userId
     * @return
     */
    public String generateToken(long userId) {
        return doGenerateToken(userId + "");
    }

    /**
     * 生成token--管理后台，使用管理员json生成，防止商城端的token被拿来管理后台越权访问
     * @param str
     * @return
     */
    public String generateToken(String str) {
        return doGenerateToken(str);
    }

    private String doGenerateToken(String str) {
        Date nowDate = new Date();
        //过期时间
        Date expireDate = new Date(nowDate.getTime() + expire * 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(str)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 解析token
     * @param token
     * @return
     */
    public Claims getClaimByToken(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (UnsupportedJwtException e) {
            throw new AuthenticationException(HEADER_TOKEN + "不正确");
        }
        catch (MalformedJwtException e) {
            throw new AuthenticationException(HEADER_TOKEN + "不是一个有效jwt");
        }
        catch (SignatureException e) {
            throw new AuthenticationException(HEADER_TOKEN + "签名校验错误");
        }
        catch (ExpiredJwtException e) {
            throw new AuthenticationException(HEADER_TOKEN + "已过期");
        }
        catch (IllegalArgumentException e) {
            throw new AuthenticationException(HEADER_TOKEN + "为空");
        }
        catch (Exception e) {
            logger.error("token解析出现未知错误"+e.getMessage(), e);
            throw new AuthenticationException(HEADER_TOKEN + "不正确");
        }
    }

    /**
     * token是否过期
     * @param expiration
     * @return
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

}
