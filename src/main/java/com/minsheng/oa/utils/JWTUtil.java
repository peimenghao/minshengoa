package com.minsheng.oa.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JWTUtil {                //密码加密解密 设置时长
    // 过期时间 1 小时
    private static final long EXPIRE_TIME = 60 * 60  * 1000;
    // 密钥
    private static final String SECRET = "SHIRO+JWT";

    /**
     * 生成 token, 5min后过期
     *
     * @param userName 用户名
     * @return 加密的token
     */
    public static String createToken(String userName) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            // 附带username信息
            return JWT.create()
                    .withClaim("userName", userName)
                    //到期时间
                    .withExpiresAt(date)
                    //创建一个新的JWT，并使用给定的算法进行标记
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 校验 token 是否正确
     * @param token    密钥
     * @param userName 用户名
     * @return 是否正确
     */
    public static boolean verify(String token, String userName) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //在token中附带了username信息
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("userName", userName)
                    .build();
            //验证 token
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息，无需secret解密也能获得，
     *
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userName").asString();//用户名转码
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}

