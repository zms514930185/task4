package com.jnshu.controller;

import com.jnshu.model.Job;
import com.jnshu.model.User;
import com.jnshu.model.vo.UserVO;
import com.jnshu.service.JobService;
import com.jnshu.service.UserService;
import com.jnshu.uitl.TencentCloudCos;
import com.jnshu.uitl.token.Token;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static org.springframework.web.util.WebUtils.getCookie;

@Controller
public class userController {
    private final Logger log = LogManager.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    JobService jobService;
    @Autowired
    TencentCloudCos tencentCloudCos;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String selectUser(Model model) throws InterruptedException, MemcachedException, TimeoutException {
        /*查询所有上架学员*/
        User user = new User();
        user.setStatus(20);
        List<User> userList = userService.selectUserSelective(user);
        /*保存VO对象的集合*/
        List<UserVO> userVOList = new ArrayList<>(16);
        /*遍历查询出的学员*/
        for (User user1 : userList) {
            /*创建VO类*/
            UserVO userVO = new UserVO();
            /*拷贝查询出的学员信息至VO类*/
            BeanUtils.copyProperties(user1, userVO);
            /*通过学员查询职业名称*/
            Job job = new Job();
            job.setId(user1.getJobId());
            List<Job> jobList = jobService.selectJobSelective(job);
            /*遍历查询学员的职业名称添加至VO类*/
            for (Job job1 : jobList) {
                userVO.setJobName(job1.getName());
            }
            userVOList.add(userVO);
        }
        model.addAttribute("userVOList", userVOList);
        log.info("上架的学员数据{}", userVOList);
        return "home";
    }

    /**
     * 查询用户信息
     */
    @RequestMapping(value = "u/user", method = RequestMethod.GET)
    public String user(HttpServletRequest request, Model model) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException, InterruptedException, MemcachedException, TimeoutException {
        /*获取请求里的cookie中的token*/
        Cookie userTokenCookie = getCookie(request, "userToken");
        /*如果有token，就获取里面的ID值*/
        if (userTokenCookie != null) {
            Token token = new Token();
            /*通过获取token里的id得到对象记录*/
            long userId = token.token(userTokenCookie);
            User user = new User();
            user.setId(userId);
            List<User> userList = userService.selectUserSelective(user);
            /*获取名字*/
            model.addAttribute("userName", userList.get(0).getName());
            /*获取头像*/
            model.addAttribute("userImg", userList.get(0).getImg());
            log.info("当前的用户是{}", userList.get(0).getName());
        }
        return "user";
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "u/user", method = RequestMethod.POST)
    public String img(HttpServletRequest request, MultipartFile file, Model model) {
        try {
            /*校验文件是否为空*/
            Validate.isTrue(!file.isEmpty(), "选择要上传的文件");
            /*获取文件的大小与类型，上传到第三方时要用*/
            long fileSize = file.getSize();
            String contentType = file.getContentType();
            /*校验文件的类型是否为图片*/
            Validate.isTrue("image".equals(contentType.substring(0,5)),"请上传为图片类型的文件");
            /*获取文件流*/
            InputStream inputStream = file.getInputStream();
            /*传入参数，使用腾讯云的SDK上传,获取上传图片的文件名*/
            String imgKey = tencentCloudCos.tencentCos(inputStream, fileSize, contentType);

            /*更新头像*/
            User user = new User();
            /*拼接url*/
            user.setImg("https://jnshu-img-1259761669.cos.ap-guangzhou.myqcloud.com/"+imgKey);
            /*获取请求里的cookie中的token*/
            Cookie userTokenCookie = getCookie(request, "userToken");
            /*获取token里面的ID值*/
            Token token = new Token();
            long userId = token.token(userTokenCookie);
            user.setId(userId);
            userService.updateByPrimaryKeySelective(user);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("imgMsg", e.getMessage());
            return "user";
        }
        return "redirect:/u/user";
    }
}