package com.jnshu.uitl;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class JjwtImpl implements Jjwt {
    /**
     * 设置一个生成token时用的密钥，对应HS512的密钥
     */
    Key KEY = new SecretKeySpec("jakarta".getBytes(), SignatureAlgorithm.HS512.getJcaName());

    @Override
    public String jjwtTokenEn(String idEn, String dateEn) {
        /*创建一个数组保存token中的头部*/
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("type", "jwtt");
        /*创建一个数组保存token中的载荷*/
        Map<String, Object> stringPayload = new HashMap<>();
        stringPayload.put("id", idEn);
        stringPayload.put("date", dateEn);
        /*创建JSON对象，将载荷转成JSON*/
        Gson gson = new Gson();
        String jjwtPayload = gson.toJson(stringPayload);
        /*生成token，三部分，第一部分是头，第二部分是载荷，第三部分是签名，密钥改成了适配HS512的可以直接用*/
        String compactJws = Jwts.builder().setHeader(stringObjectMap)
                .setPayload(jjwtPayload)
                .signWith(SignatureAlgorithm.HS512, KEY).compact();
        return compactJws;
    }

    /**
     *获取token里的信息
     */
    @Override
    public Claims jjwtTokenDe(String token) {
        Claims claims = Jwts.parser()
                //设置签名的秘钥，和加密的时候一样
                .setSigningKey(KEY)
                //设置需要解析的token
                .parseClaimsJws(token).getBody();
        return claims;
    }
}