package com.jnshu.service;

import com.jnshu.model.Job;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")//加载配置文件
class JobServiceImplTest {

    private final Logger logger = LogManager.getLogger(this.getClass());
    /*private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
    private JobService jobService = zms.getBean(JobService.class);*/

    @Autowired
    JobService jobService;

    @Test
    void selectJobSelective() {
        Job job = new Job();
        job.setId(11L);
        /*job.setCategory(1);*/
        long time=System.currentTimeMillis();
        List<Job> jobList = jobService.selectJobSelective(job);
        long time1=System.currentTimeMillis()-time;
        logger.info("时间花费：{}——职业信息{}",time1,jobList);
        List<Job> jobList1 = jobService.selectJobSelective(job);
        long time2=System.currentTimeMillis()-time1-time;
        logger.info("时间花费：{}——职业信息{}",time2,jobList1);
    }

    @Test
    void deleteByPrimaryKey(){
        long id=11L;
        jobService.deleteByPrimaryKey(id);
    }
}
