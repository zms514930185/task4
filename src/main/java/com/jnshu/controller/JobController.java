package com.jnshu.controller;

import com.jnshu.model.Job;
import com.jnshu.service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class JobController {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    JobService jobService;

    @RequestMapping(value = "/u/job",method = RequestMethod.GET)
    public String selectJob(Model model,Integer category){
        Job job=new Job();
        job.setCategory(category);
        List<Job> jobList=jobService.selectJobSelective(job);
        model.addAttribute("jobList",jobList);
        logger.info("查询出的职业{}",jobList);
        return "job";
    }
}
