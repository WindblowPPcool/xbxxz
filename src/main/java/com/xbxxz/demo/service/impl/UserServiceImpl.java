package com.xbxxz.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xbxxz.demo.entity.User;
import com.xbxxz.demo.mapper.UserMapper;
import com.xbxxz.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int register(User user) {
        if(user.getUsername() == null || user.getUsername().isEmpty()){
            return -1;
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if(userMapper.selectCount(queryWrapper)>0){
            return 0;
        }
        return userMapper.insert(user);
    }

    @Override
    public int login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if(userMapper.selectCount(queryWrapper)==0){
            return -1;
        }
        queryWrapper.eq("password", user.getPassword());
        if(userMapper.selectCount(queryWrapper)==0){
            return 0;
        }
        return userMapper.selectOne(queryWrapper).getId();
    }
}
