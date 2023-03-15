package org.example.objectpool.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.objectpool.mapper.UserMapper;
import org.example.objectpool.pojo.User;
import org.example.objectpool.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

/**
 * @author lch
 * @date 2023年03月15日 22:37
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public JSONResult getUserInfo(String userName) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("username", userName);

        User user = userMapper.selectOneByExample(example);
        log.info("===========> getUserInfo");
        return JSONResult.ok(user);
    }
}
