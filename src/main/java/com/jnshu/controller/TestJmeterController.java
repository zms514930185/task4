package com.jnshu.controller;

import com.google.gson.Gson;
import com.jnshu.model.User;
import com.jnshu.service.UserService;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeoutException;

@Controller
public class TestJmeterController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/jmeter" , method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public String user(User user) throws InterruptedException, MemcachedException, TimeoutException {
        long time=System.currentTimeMillis();
        Gson gson=new Gson();
        List<User> userList =userService.selectUserSelective(user);
        logger.info("查询时间共{}毫秒", System.currentTimeMillis()-time);
        return gson.toJson(userList.get(0).getName());
    }
}