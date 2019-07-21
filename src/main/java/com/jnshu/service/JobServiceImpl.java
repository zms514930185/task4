package com.jnshu.service;

import com.jnshu.mapper.JobMapper;
import com.jnshu.model.Job;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Resource
    JobMapper jobMapper;
    @Override
    public List<Job> selectJobSelective(Job job) {
        return jobMapper.selectJobSelective(job);
    }
}
