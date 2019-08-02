package com.jnshu.service;

import com.jnshu.model.User;

import java.util.List;

public interface UserService {
    List<User> selectUserSelective(User user);
    int insert(User record);
    int insertSelective(User record);
}
