package com.staceyho.springbootmall.service.impl;

import com.staceyho.springbootmall.dao.UserDao;
import com.staceyho.springbootmall.dto.UserRegisterRequest;
import com.staceyho.springbootmall.model.User;
import com.staceyho.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }
}
