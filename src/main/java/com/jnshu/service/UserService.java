package com.jnshu.service;

import com.jnshu.model.User;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.util.List;
import java.util.concurrent.TimeoutException;

public interface UserService {
    List<User> selectUserSelective(User user) throws InterruptedException, MemcachedException, TimeoutException;
    int insert(User record);
    int insertSelective(User record);
    int updateByPrimaryKeySelective(User record);
}
