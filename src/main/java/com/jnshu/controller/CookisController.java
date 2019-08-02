package com.jnshu.controller;

import com.jnshu.model.User;
import com.jnshu.service.UserService;
import com.jnshu.uitl.DesUitlImpl;
import com.jnshu.uitl.JjwtImpl;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CookisController {

    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        return "hello";
    }

    /**
     * 登录接口
     */
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String logIn(HttpServletResponse response,String account, String pwd) {
        try {
            /*校验：帐号与密码不能为空*/
            Validate.isTrue(account != null && pwd != null, "帐号与密码不能为空");
            /*查此帐号是否存在*/
            User user = new User();
            user.setAccount(account);
            logger.info("要查的帐号{}", account);
            List<User> userList = userService.selectUserSelective(user);
            logger.info("查询出的帐号记录{}", userList);
            /*校验：数据库里是否有此帐号*/
            Validate.notEmpty(userList, "此帐号未注册");
            /*将输入密码进行MD5加密*/
            String pwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
            /*校验：输入的密码与数据库密码是否相等*/
            Validate.isTrue(userList.get(0).getPwd().equals(pwdMd5),"密码或帐号错误");
            /*登录成功返回Cookie,先把ID和当前时间转成String类型再进行加密*/
            DesUitlImpl desUitl=new DesUitlImpl();
            String sessionId=desUitl.encryption(Long.toString(userList.get(0).getId()));
            String sessionDate=desUitl.encryption(Long.toString(System.currentTimeMillis()));
            logger.info("加密后的ID：{}与时间{}",sessionId,sessionDate);
            /*生成token，只做最简单的传id与时间*/
            JjwtImpl jjwt=new JjwtImpl();
            String userToken=jjwt.jjwtTokenEn(sessionId,sessionDate);
            logger.info("生成的token:{}",userToken);
            /*将token传入Cookie,创建Cookie对象,初始化此Cookie的name与value*/
            Cookie cookie=new Cookie("userToken",userToken);
            /*设置过期时间，单位秒*/
            cookie.setMaxAge(60*60*24*30);
            /*作用域，此域都可启用*/
            cookie.setPath("/");
            /*此接口正常响应时，就把cookie里的数据返回到客户端中*/
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "hello";
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/hello/registered", method = RequestMethod.POST)
    public String registered(String account, String pwd, String name) {
        /*进行MD5加密*/
        String pwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
        logger.info("md5加密后的值：{}", pwdMd5);
        User user = new User();
        user.setAccount(account);
        /*保存的密码为加密后的值*/
        user.setPwd(pwdMd5);
        user.setName(name);
        logger.info("要增加的值{}", user);
        userService.insertSelective(user);
        logger.info("新增的记录ID是{}", user.getId());
        return "hello";
    }
}