package com.staceyho.springbootmall.dao;

import com.staceyho.springbootmall.dto.UserRegisterRequest;
import com.staceyho.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    User getUserByEmail(String email);

    Integer createUser(UserRegisterRequest userRegisterRequest);
}
