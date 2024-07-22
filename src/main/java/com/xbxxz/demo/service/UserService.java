package com.xbxxz.demo.service;

import com.xbxxz.demo.entity.User;

public interface UserService {
    int register(User user);

    int login(User user);
}
