package com.jnshu.service;

import com.jnshu.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

class UserServiceImplTest {
    private final Logger logger = LogManager.getLogger(this.getClass());
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private UserService userService = zms.getBean(UserService.class);

    @Test
    void selectUserSelective() {
        User user=new User();
        user.setId(1L);
        logger.info(userService.selectUserSelective(user));
    }


}