package com.staceyho.springbootmall.service;

import com.staceyho.springbootmall.dto.UserLoginRequest;
import com.staceyho.springbootmall.dto.UserRegisterRequest;
import com.staceyho.springbootmall.model.User;

public interface UserService {

    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
    User login(UserLoginRequest userLoginRequest);
}
