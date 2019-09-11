package com.jnshu.controller;

import com.jnshu.model.CheckNum;
import com.jnshu.model.User;
import com.jnshu.service.CheckNumService;
import com.jnshu.service.UserService;
import com.jnshu.uitl.DesUitlImpl;
import com.jnshu.uitl.JjwtImpl;
import com.jnshu.uitl.encoder.SMS;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Controller
public class CookisController {

    private final Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    CheckNumService checkNumService;
    @Autowired
    SMS sms;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    /**
     * 登录接口
     */
    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String logIn(HttpServletResponse response, String account, String pwd,Model model) {
        try {
            log.info("登录的帐号与密码{}",account+"、"+pwd);
            Validate.matchesPattern(account,"^[A-Za-z][A-Za-z0-9]{4,14}","请输入正确的帐号，以字母开头，可包含数字的5~15位帐号");
            Validate.matchesPattern(pwd,"^[A-Za-z][A-Za-z0-9_~!@#$%]{7,14}","请输入正确的密码，以字母开头，可包含特殊字符的8~15位密码");

            /*查此帐号是否存在*/
            User user = new User();
            user.setAccount(account);
            log.info("要登录的帐号{}", account);
            List<User> userList = userService.selectUserSelective(user);
            log.info("查询出的帐号记录{}", userList);
            /*校验：数据库里是否有此帐号*/
            Validate.notEmpty(userList, "此帐号未注册");
            /*将输入密码进行MD5加密*/
            String pwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
            /*校验：输入的密码与数据库密码是否相等*/
            Validate.isTrue(userList.get(0).getPwd().equals(pwdMd5), "密码或帐号错误");
            /*登录成功返回Cookie,先把ID和当前时间转成String类型再进行加密*/
            DesUitlImpl desUitl = new DesUitlImpl();
            String sessionId = desUitl.encryption(Long.toString(userList.get(0).getId()));
            String sessionDate = desUitl.encryption(Long.toString(System.currentTimeMillis()));
            log.info("加密后的ID：{}与时间{}", sessionId, sessionDate);
            /*生成token，只做最简单的传id与时间*/
            JjwtImpl jjwt = new JjwtImpl();
            String userToken = jjwt.jjwtTokenEn(sessionId, sessionDate);
            log.info("生成的token:{}", userToken);
            /*将token传入Cookie,创建Cookie对象,初始化此Cookie的name与value*/
            Cookie cookie = new Cookie("userToken", userToken);
            /*设置过期时间，单位秒*/
            cookie.setMaxAge(60 * 60 * 24 * 30);
            /*作用域，此域都可启用*/
            cookie.setPath("/");
            /*此接口正常响应时，就把cookie里的数据返回到客户端中*/
            response.addCookie(cookie);
        } catch (IllegalArgumentException e){
            String msg=e.getMessage();
            log.info(msg);
            model.addAttribute("msg",msg);
            return "hello";
        } catch (InvalidKeyException | NoSuchAlgorithmException | TimeoutException | BadPaddingException | MemcachedException | NoSuchPaddingException | IllegalBlockSizeException | InterruptedException e) {
            e.printStackTrace();
        }
        return "home";
    }

    /**
     * 注册
     */
    @RequestMapping(value = "/hello/registered", method = RequestMethod.POST)
    public String registered(String account, String pwd, String name,String phone,String code,Model model) {
        try {
            log.info("要注册的帐号、密码、名字、手机号、验证码：{}",account+"、"+phone+"、"+name+"、"+phone+"、"+code);
            /*校验输入的帐号，密码，手机号，验证码的正确性*/
            Validate.matchesPattern(account,"^[A-Za-z][A-Za-z0-9]{4,14}","请输入正确的帐号，以字母开头，可包含数字的5~15位帐号");
            Validate.matchesPattern(pwd,"^[A-Za-z][A-Za-z0-9_~!@#$%]{7,14}","请输入正确的密码，以字母开头，可包含特殊字符的8~15位密码");
            Validate.matchesPattern(phone,"^1[3-9][0-9]{9}","请输入正确手机号");
            Validate.matchesPattern(code,"^[0-9]{4}","请输入四位验证码");

            /*校验帐号是否重复*/
            User user=new User();
            user.setAccount(account);
            List<User> userList = userService.selectUserSelective(user);
            Validate.isTrue(userList.isEmpty(),"此帐号已被注册，请更换帐号");

            /*检验此验证码是否存在*/
            CheckNum checkNum=new CheckNum();
            checkNum.setPhone(phone);
            List<CheckNum> checkNumList=checkNumService.selectSelective(checkNum);
            Validate.isTrue(!checkNumList.isEmpty(),"未成功获取验证码");
            /*校验验证码输入的时间是否为3分钟内输入*/
            Validate.isTrue(checkNumList.get(0).getUpdateAt()>System.currentTimeMillis()-1800000,"验证时间超时");
           /*校验验证码的正确性*/
            Validate.isTrue(code.equals(checkNumList.get(0).getCode()),"验证码错误");

            /*密码进行MD5加密*/
            String pwdMd5 = DigestUtils.md5DigestAsHex(pwd.getBytes());
            /*保存加密后的密码*/
            user.setPwd(pwdMd5);
            user.setName(name);
            user.setPhone(phone);
            userService.insertSelective(user);
            log.info("新增的记录是{}", user);
            model.addAttribute("registeredMsg","注册成功");
        }catch (IllegalArgumentException e){
            String msg=e.getMessage();
            model.addAttribute("registeredMsg",msg);
            return "hello";
        } catch (InterruptedException | MemcachedException | TimeoutException e) {
            e.printStackTrace();
        }
        return "hello";
    }

    /**
     * 发送验证码短信
     */
    @RequestMapping(value = "/check/phone", method = RequestMethod.POST)
    public String checkPhone(String phone, Model model) throws InterruptedException, MemcachedException, TimeoutException {
        log.info("要验证的手机号：{}", phone);
        Map<String, String> map = new HashMap<>();
        try {
            Validate.matchesPattern(phone, "\\d{11}$", "手机号位数错误");
            Validate.matchesPattern(phone, "^1(3|4|5|7|8)\\d{9}$", "手机号错误");
            /*第一步判断用户表，判断至手机号是否已注册*/
            User user = new User();
            user.setPhone(phone);
            List<User> userList = userService.selectUserSelective(user);
            Validate.isTrue(userList.isEmpty(), "此手机号已被注册");

            /*第二步，判断验证码表是否已有此手机号*/
            CheckNum checkNum = new CheckNum();
            checkNum.setPhone(phone);
            List<CheckNum> checkList = checkNumService.selectSelective(checkNum);
            if (!checkList.isEmpty()) {
                /*表里有此手机号记录判断验证次数，需要验证次数不超过3次或者数据库更新日期不与今天相同*/
                Validate.isTrue(checkList.get(0).getPhoneNum() < 4 || !new LocalDate().equals(new LocalDate(checkList.get(0).getUpdateAt())), "今日验证次数已用完");
                /*如果当前发送验证码时间与数据库日期不同，即初始化验证次数*/
                if (!new LocalDate().equals(new LocalDate(checkList.get(0).getUpdateAt()))) {
                    CheckNum newDateCheckNum = new CheckNum();
                    newDateCheckNum.setPhoneNum(0);
                    newDateCheckNum.setPhone(phone);
                    checkNumService.updateSelective(newDateCheckNum);
                }
            } else {
                /*表里没有此手机号记录即新增此记录，且初始化验证次数*/
                checkNum.setPhoneNum(0);
                /*新增此手机号至验证表*/
                checkNumService.insertSelective(checkNum);
            }
            /*校验通过开始发送手机验证码,判断验证码是否发送成功*/
            Map<String, String> smsCheckMap = sms.sms(phone);
            smsCheckMap.get("code");
            /*状态码等于000000才是成功*/
            Validate.isTrue("000000".equals(smsCheckMap.get("code")), "验证码发送失败:" + smsCheckMap.get("msg"));
            /*提取验证码*/
            String phoneNum = smsCheckMap.get("num");

            /*获取参数最新记录，将参数更新至验证码表*/
            CheckNum newCheckNum = new CheckNum();
            newCheckNum.setPhone(phone);
            List<CheckNum> checkList1 = checkNumService.selectSelective(newCheckNum);
            newCheckNum.setPhoneNum(checkList1.get(0).getPhoneNum() + 1);
            newCheckNum.setCode(phoneNum);
            /*更新验证码表*/
            checkNumService.updateSelective(newCheckNum);
            log.info(newCheckNum);
            model.addAttribute("checkMsg","发送验证码成功");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String msg = e.getMessage();
            map.put("msg",msg);
            map.put("code", "-1");
            model.addAttribute("checkMsg",msg);
            log.info("{}", msg);
            return "hello";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "hello";
    }
}