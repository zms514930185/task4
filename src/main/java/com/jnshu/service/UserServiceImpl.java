package com.jnshu.service;

import com.jnshu.mapper.UserMapper;
import com.jnshu.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public List<User> selectUserSelective(User user) {
        return userMapper.selectUserSelective(user);
    }
}
