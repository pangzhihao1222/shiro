package com.example.demo_shiro.service;

import com.example.demo_shiro.model.UserDO;

public interface UserService {
    /**
     * 获取用户全部信息
     * @param username
     * @return
     */
    UserDO findAllUserInfoByUsername(String username);

    /**
     * 获取用户基本信息
     * @param userId
     * @return
     */
    UserDO findSimpleUserInfoById(int userId);

    /**
     * 根据用户名查找用户信息
     * @param username
     * @return
     */
    UserDO findSimpleUserInfoByUsername(String username);
}
