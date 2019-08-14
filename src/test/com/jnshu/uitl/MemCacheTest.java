//package com.jnshu.uitl;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.jnshu.model.User;
//import com.jnshu.service.UserService;
//import com.jnshu.service.UserServiceImpl;
//import net.rubyeye.xmemcached.MemcachedClient;
//import net.rubyeye.xmemcached.exception.MemcachedException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//import java.util.concurrent.TimeoutException;
//
///*@RunWith(SpringRunner.class)
//@ContextConfiguration({"classpath:applicationContext.xml"})*/
//public class MemCacheTest {
//    private final Logger logger = LogManager.getLogger(this.getClass());
//
//    /*@Autowired
//    private MemcachedClient memcachedClient;*/
//    private ApplicationContext zms = new ClassPathXmlApplicationContext("applicationContext.xml");
//    private MemcachedClient memcachedClient = zms.getBean(MemcachedClient.class);
//    private UserServiceImpl userServiceImpl = zms.getBean(UserServiceImpl.class);
//    /*测试已过期，不可使用*/
//    @Test
//    public void operate() throws InterruptedException, MemcachedException, TimeoutException, TimeoutException, MemcachedException {
//        memcachedClient.set("hello", 0, "Hello,cluster xmemcached");
//        String value = memcachedClient.get("hello");
//        System.out.println("hello=" + value);
//        memcachedClient.delete("hello");
//        value = memcachedClient.get("hello");
//        System.out.println("hello=" + value);
//
//        /*根据ID查询一个用户*/
//        User user=new User();
//        /*查询ID=9的用户*/
//        long userId=9;
//        user.setId(userId);
//        long time=System.currentTimeMillis();
//        if(memcachedClient.get("userId="+userId)==null){
//            logger.info("userId={}的数据未缓存",userId);
//            List<User> userList=userServiceImpl.selectUserSelective(user);
//            String usetName=userList.get(0).getName();
//            logger.info("查询到的数据的昵称为{}",usetName);
//            logger.info("获取到的时间为{}",System.currentTimeMillis()-time);
//            /*序列化后进行保存*/
//            Gson gson=new Gson();
//            String value1=gson.toJson(userList);
//            memcachedClient.set("userId="+userId,0,value1);
//            logger.info("将userId={}储存至缓存中",userId);
//        }else {
//            logger.info("userId={}的数据已缓存",userId);
//            String value2=memcachedClient.get("userId="+userId);
//            logger.info("数据内容是{}",value2);
//            logger.info("获取到的时间为{}",System.currentTimeMillis()-time);
//            /*反序列化进行提取指定内容*/
//            Gson gson=new Gson();
//            List<User> userList=gson.fromJson(value2,new TypeToken<List<User>>() {}.getType());
//            String usetName=userList.get(0).getName();
//            logger.info("查询到的数据的昵称为{}",usetName);
//            /*删除缓存中的值*/
//            memcachedClient.delete("userId="+userId);
//        }
//        memcachedClient.set("user"+null,0,"value");
//        String value3=memcachedClient.get("user"+null);
//        System.out.println("测试null"+value3);
//    }
//    @Test
//    void userTest() throws InterruptedException, MemcachedException, TimeoutException {
//        long time=System.currentTimeMillis();
//        User user=new User();
//        //user.setStatus(20);
//        user.setAccount("1");
//        List<User> userList=userServiceImpl.selectUserSelective(user);
//        logger.info("耗时{}毫秒，查询的内容为：{}",System.currentTimeMillis()-time,userList);
//    }
//}