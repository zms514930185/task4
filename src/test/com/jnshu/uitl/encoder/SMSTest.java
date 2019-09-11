package com.jnshu.uitl.encoder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath*:*applicationContext.xml")//加载配置文件
class SMSTest {

    @Autowired
    SMS sms;

    @Test
    void sms() throws IOException {
        String phone="18811055925";
        Map smsMap = sms.sms(phone);
        System.out.println(smsMap.get("msg"));
    }
}