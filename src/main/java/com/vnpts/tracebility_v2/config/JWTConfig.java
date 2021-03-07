package com.vnpts.tracebility_v2.config;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JWTConfig {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "KYIVtA7915PUEWTqTca74sENpw48u7nkzSJiJmitF7D2PJWOu";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";


    public static String genToken(Object user) {
        Gson gson = new Gson();
        String JWT = Jwts.builder().setSubject(gson.toJson(user))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        return JWT;
    }

    public static String decodeToken(String token) {
        String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody()
                .getSubject();
        return user;
    }
}
