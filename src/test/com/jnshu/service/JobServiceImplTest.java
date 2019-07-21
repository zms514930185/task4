package com.jnshu.service;

import com.jnshu.model.Job;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JobServiceImplTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private JobService jobService = zms.getBean(JobService.class);

    @Test
    void selectJobSelective() {

        try {
            Map map=new HashMap();
            for (int i = 1; i < 10; i++) {
                Job job = new Job();
                job.setCategory(i);
                List<Job> jobList = jobService.selectJobSelective(job);
                if (jobList.isEmpty()) {
                    break;
                }else {
                    map.put(i,jobList);
                    //logger.info(jobList);
                }
            }
            logger.info(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}