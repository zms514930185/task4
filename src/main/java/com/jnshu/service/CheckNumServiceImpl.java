package com.jnshu.service;

import com.jnshu.mapper.CheckNumMapper;
import com.jnshu.model.CheckNum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CheckNumServiceImpl implements CheckNumService {
    @Resource
    CheckNumMapper checkNumMapper;
    @Override
    public List<CheckNum> selectSelective(CheckNum checkNum) {
        return checkNumMapper.selectSelective(checkNum);
    }

    @Override
    public int insertSelective(CheckNum checkNum) {
        checkNum.setCreateAt(System.currentTimeMillis());
        checkNum.setUpdateAt(System.currentTimeMillis());
        return checkNumMapper.insertSelective(checkNum);
    }

    @Override
    public int updateSelective(CheckNum checkNum) {
        checkNum.setUpdateAt(System.currentTimeMillis());
        return checkNumMapper.updateSelective(checkNum);
    }
}
