package com.jnshu.service;

import com.jnshu.model.CheckNum;

import java.util.List;

public interface CheckNumService {
    List<CheckNum> selectSelective(CheckNum record);
    int insertSelective(CheckNum record);
    int updateSelective(CheckNum record);
}
