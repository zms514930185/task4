package com.jnshu.uitl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.clients.jedis.Jedis;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")//加载配置文件
 public class redisTest {
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    /*@Test
     void redis(){
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");

        //查看服务是否运行
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("name_1","名字1");
        System.out.println(jedis.get("name_1"));
    }*/

    @Test
     void redisSpring(){
        redisTemplate.opsForValue().set("name_2","名字2");
        System.out.println(redisTemplate.opsForValue().get("name_2"));
        System.out.println(redisTemplate.opsForValue().get("1"));
    }
}