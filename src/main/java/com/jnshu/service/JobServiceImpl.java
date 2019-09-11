package com.jnshu.service;

import com.jnshu.mapper.JobMapper;
import com.jnshu.model.Job;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class JobServiceImpl implements JobService {
    @Resource
    JobMapper jobMapper;

    @Override
    @Cacheable(value = "job",key = "'job_id:'+#job.id+':category:'+#job.category")
    public List<Job> selectJobSelective(Job job) {
        return jobMapper.selectJobSelective(job);
    }

    @Override
    @CacheEvict(value = "job",key = "'job_id:'+#id+':category:null'")
    public int deleteByPrimaryKey(Long id) {
        return jobMapper.deleteByPrimaryKey(id);
    }
}
