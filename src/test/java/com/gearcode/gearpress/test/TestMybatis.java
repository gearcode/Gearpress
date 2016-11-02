package com.gearcode.gearpress.test;

import com.alibaba.fastjson.JSON;
import com.gearcode.gearpress.dao.UserMapper;
import com.gearcode.gearpress.domain.User;
import com.gearcode.gearpress.domain.UserExample;
import com.gearcode.gearpress.util.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by liteng3 on 2016/11/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring.xml")
public class TestMybatis {
    private static final Logger logger = LoggerFactory.getLogger(TestMybatis.class);

    @Autowired
    IdWorker idWorker;

    @Autowired
    UserMapper userMapper;

    @Test
    public void testInterest(){
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo("liteng");
        List<User> users = userMapper.selectByExample(example);

        logger.info("User liteng: {}", JSON.toJSONString(users));
    }

}
