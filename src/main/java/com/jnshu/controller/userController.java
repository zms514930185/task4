package com.jnshu.controller;

import com.jnshu.model.Job;
import com.jnshu.model.User;
import com.jnshu.model.vo.UserVO;
import com.jnshu.service.JobService;
import com.jnshu.service.UserService;
import com.jnshu.uitl.DesUitlImpl;
import com.jnshu.uitl.JjwtImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.springframework.web.util.WebUtils.getCookie;

@Controller
public class userController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    JobService jobService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String selectUser(HttpServletRequest request,Model model) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        /*获取请求里的cookie中的token*/
        Cookie userTokenCookie = getCookie(request, "userToken");
        /*如果有token，就获取里面的ID值*/
        if(userTokenCookie!=null){
            /*获取Token*/
            String userToken= Objects.requireNonNull(userTokenCookie).getValue();
            JjwtImpl jjwt=new JjwtImpl();
            /*解密token,获取到加密后的Token值*/
            String userEnId= (String) jjwt.jjwtTokenDe(userToken).get("id");
            /*进行解密,使用解密方法*/
            DesUitlImpl desUitl=new DesUitlImpl();
            String userIdDe=desUitl.decrypt(userEnId);
            /*把id值从String转成long*/
            long userId = Long.parseLong(userIdDe);
            /*查询对应的ID值的数据*/
            User user=new User();
            user.setId(userId);
            List<User> userList=userService.selectUserSelective(user);
            /*获取名字*/
            String userName=userList.get(0).getName();
            logger.info("当前登入的用户：{}",userName);
            model.addAttribute("userName",userName);
        }

        /*查询所有上架学员*/
        User user=new User();
        user.setStatus(20);
        List<User> userList=userService.selectUserSelective(user);
        /*保存VO对象的集合*/
        List<UserVO> userVOList= new ArrayList<>(16);
        /*遍历查询出的学员*/
        for(User user1:userList){
            /*创建VO类*/
            UserVO userVO=new UserVO();
            /*拷贝查询出的学员信息至VO类*/
            BeanUtils.copyProperties(user1,userVO);
            /*通过学员查询职业名称*/
            Job job=new Job();
            job.setId(user1.getJobId());
            List<Job> jobList=jobService.selectJobSelective(job);
            /*遍历查询学员的职业名称添加至VO类*/
            for (Job job1:jobList){
                userVO.setJobName(job1.getName());
            }
            userVOList.add(userVO);
        }
        model.addAttribute("userVOList",userVOList);
        logger.info("上架的学员数据{}",userVOList);
        return "home";
    }
}