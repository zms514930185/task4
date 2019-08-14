package com.jnshu.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jnshu.mapper.UserMapper;
import com.jnshu.model.User;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    MemcachedClient memcachedClient;
    //private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public List<User> selectUserSelective(User user) throws InterruptedException, MemcachedException, TimeoutException {
        try {
            /*用户缓存，键：getUser_id_帐号_姓名_状态*/
            /*if语句：查询的是否只有需要缓存的内容，如果不是直接查数据库*/
            if (user.getPwd() == null && user.getJobId() == null && user.getCreateAt() == null && user.getCreateBy() == null && user.getImg() == null && user.getIpo() == null && user.getProgress() == null && user.getUpdateAt() == null && user.getUpdateBy() == null) {
                /*如果是可缓存的内容判断是否有此值*/
                if (memcachedClient.get("getUser_" + user.getId() + "_" + user.getAccount() + "_" + user.getName() + "_" + user.getStatus()) != null) {
                    /*获取缓存内容*/
                    String userValue = memcachedClient.get("getUser_" + user.getId() + "_" + user.getAccount() + "_" + user.getName() + "_" + user.getStatus());
                    Gson gson = new Gson();
                    /*把缓存中的内容反序列化后再返回*/
                    return gson.fromJson(userValue, new TypeToken<List<User>>() {
                    }.getType());
                } else {
                    /*通过数据库查询出数据*/
                    List<User> userList = userMapper.selectUserSelective(user);
                    Gson gson = new Gson();
                    /*数据序列化*/
                    String userValue = gson.toJson(userList);
                    /*保存至缓存*/
                    memcachedClient.set("getUser_" + user.getId() + "_" + user.getAccount() + "_" + user.getName() + "_" + user.getStatus(), 0, userValue);
                    return userList;
                }
            }
        }
        /*如果Memcached缓存出错，就直接查数据库*/
        catch (MemcachedException e){
            /*通过数据库查询出数据*/
            return userMapper.selectUserSelective(user);
        }
        /*如果没有要走缓存的数据，直接走数据库*/
        return userMapper.selectUserSelective(user);
    }
    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }
}
