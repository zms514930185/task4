package com.jnshu.controller;


import com.jnshu.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@Controller
public class ImgController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "u/img", method = RequestMethod.GET)
    public String imgGet() {
        return "img";
    }

    /**
     * 上传图片
     */
    @RequestMapping(value = "u/img", method = RequestMethod.POST)
    public String img(MultipartFile file, Model model) {
        try {
            Validate.isTrue(!file.isEmpty(), "选择要上传的文件");
            System.out.println(file.getOriginalFilename());
            System.out.println(file.getContentType());

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("imgMsg", e.getMessage());
        }
        return "img";
    }
}