package com.jnshu.service;

import com.jnshu.model.User;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.TimeoutException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")//加载配置文件
public class UserServiceImplTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Test
    public void selectUserSelective() throws InterruptedException, MemcachedException, TimeoutException {
        User user=new User();
        //user.setId(1L);
        logger.info(userService.selectUserSelective(user));
    }
}