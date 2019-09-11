package com.jnshu.service;

import com.jnshu.model.CheckNum;
import com.jnshu.model.User;
import com.jnshu.uitl.encoder.SMS;
import lombok.extern.log4j.Log4j2;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
//加载配置文件
class CheckNumServiceImplTest {
    @Autowired
    CheckNumService checkNumService;
    @Autowired
    UserService userService;
    @Autowired
    SMS sms;

    @Test
    void selectSelective() throws InterruptedException, MemcachedException, TimeoutException {
        try {
            String phone = "18811055925";
            log.info("验证的手机号:{}",phone);
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
                /*判断是否超时，超过3分钟即为超时*/
                //Validate.isTrue(checkList.get(0).getUpdateAt()>System.currentTimeMillis()-1800000,"验证时间超时");
                /*如果当前发送验证码时间与数据库日期不同，即初始化验证次数*/
                if(!new LocalDate().equals(new LocalDate(checkList.get(0).getUpdateAt()))){
                    CheckNum newDateCheckNum=new CheckNum();
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
            CheckNum newCheckNum=new CheckNum();
            newCheckNum.setPhone(phone);
            List<CheckNum> checkList1 = checkNumService.selectSelective(newCheckNum);
            newCheckNum.setPhoneNum(checkList1.get(0).getPhoneNum()+1);
            newCheckNum.setCode(phoneNum);
            /*更新验证码表*/
            checkNumService.updateSelective(newCheckNum);
            log.info(newCheckNum);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            String message = e.getMessage();
            log.info("{}", message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateCheck(){
        CheckNum checkNum=new CheckNum();
        checkNum.setPhone("13860656205");
        checkNum.setCode("8888");
        checkNumService.updateSelective(checkNum);
    }
}