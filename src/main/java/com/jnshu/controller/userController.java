package com.jnshu.controller;

import com.jnshu.model.Job;
import com.jnshu.model.User;
import com.jnshu.model.vo.UserVO;
import com.jnshu.service.JobService;
import com.jnshu.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class userController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    JobService jobService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String selectUser(Model model) {
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
        logger.info("查询出的学员数据{}",userVOList);
        return "home";
    }
}