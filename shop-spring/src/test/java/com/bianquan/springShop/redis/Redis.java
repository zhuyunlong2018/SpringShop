package com.bianquan.springShop.redis;


import com.bianquan.springShop.common.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Redis {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void insert() {
        String key = "test-redis-1";
        String test1 = redisUtil.get(key);
        System.out.println(test1);
        redisUtil.set(key, "for test");
        System.out.println(redisUtil.get(key));
    }
}
