package com.jnshu.controller;

import com.jnshu.model.User;
import com.jnshu.service.UserService;
import com.jnshu.uitl.token.Token;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.springframework.web.util.WebUtils.getCookie;

@Controller
public class UserTokenIDController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;

    /**
     * 获取token中的id，查询对应用户信息
     */
    @RequestMapping(value = "/u/id", method = RequestMethod.GET)
    public void userTokenId(HttpServletRequest request, Model model) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InterruptedException, MemcachedException, TimeoutException {
        /*获取请求里的cookie中的token*/
        Cookie userTokenCookie = getCookie(request, "userToken");
        /*如果有token，就获取里面的ID值*/
        if(userTokenCookie!=null){
            Token token=new Token();
            /*获取token里的对象*/
            long userId = token.token(userTokenCookie);
            User user=new User();
            user.setId(userId);
            List<User> userList=userService.selectUserSelective(user);
            /*获取名字*/
            logger.info("当前登入的用户：{}",userList.get(0).getName());
        }
    }
}
