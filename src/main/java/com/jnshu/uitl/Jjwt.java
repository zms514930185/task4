package com.jnshu.uitl;

import io.jsonwebtoken.Claims;

interface Jjwt {
    /**
     * 加密token
     * @param idEn 要加密的ID
     * @param dateEn 要加密的时间
     */
    String jjwtTokenEn(String idEn, String dateEn);

    /**
     * 解密token
     * @param token 要解密的token值
     */
    Claims jjwtTokenDe(String token);
}
