package com.jnshu.uitl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JwtTest {
    Key KEY = new SecretKeySpec("javastack".getBytes(),SignatureAlgorithm.HS512.getJcaName());

    @Test
    void key(){
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("type", "jwtt");
        String payload = "{\"user_id\":\"1341137\", \"expire_time\":\"2018-01-01 0:00:00\"}";
        /*头部，载荷，签名*/
        String compactJws = Jwts.builder().setHeader(stringObjectMap)
                .setPayload(payload)
                .signWith(SignatureAlgorithm.HS512, KEY).compact();

        System.out.println("jwt key:" + new String(KEY.getEncoded()));
        System.out.println("jwt payload:" + payload);
        System.out.println("jwt encoded:" + compactJws);
    }
}