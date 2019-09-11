package com.jnshu.uitl.token;

import com.jnshu.uitl.DesUitlImpl;
import com.jnshu.uitl.JjwtImpl;
import lombok.extern.log4j.Log4j2;
import net.rubyeye.xmemcached.exception.MemcachedException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

@Log4j2
public class Token {

    public long token(Cookie userCookieToken) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InterruptedException, MemcachedException, TimeoutException {
        /*获取Token*/
        String userToken = Objects.requireNonNull(userCookieToken).getValue();
        JjwtImpl jjwt = new JjwtImpl();
        /*解密token,获取到加密后的Token值*/
        String userEnId = (String) jjwt.jjwtTokenDe(userToken).get("id");
        /*进行解密,使用解密方法*/
        DesUitlImpl desUitl = new DesUitlImpl();
        String userIdDe = desUitl.decrypt(userEnId);
        /*把id值从String转成long*/
        return Long.parseLong(userIdDe);
    }
}
