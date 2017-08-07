package com.sirier.redis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sirierx on 2017/8/7.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-redis.xml" })
public class JedisTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test // 存储数据
    public void test1() {
        redisTemplate.opsForValue().set("sh123", "上海", 300, TimeUnit.SECONDS);
        System.out.println("set ok");
    }

    @Test // 获取redis数据
    public void test2() {
        String val = redisTemplate.opsForValue().get("sh123");
        System.out.println("get ok  --->" + val);
    }


    /**
     * 这个main方法,测试redis是否完整整合进入spring中
     * @param args
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext c = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        Object bean = c.getBean("redisTemplate");
        System.out.println(bean);// 获取redis模块对象 检测环境配置 jar版本是否兼容
    }

}
