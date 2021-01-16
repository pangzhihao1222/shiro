package com.example.demo_shiro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo_shiro.mapper.RoleMapper;
import com.example.demo_shiro.mapper.UserMapper;
import com.example.demo_shiro.model.RoleDO;
import com.example.demo_shiro.model.UserDO;
import com.example.demo_shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDO findAllUserInfoByUsername(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper();
        UserDO userDO = userMapper.selectOne(queryWrapper.eq("username",username));
        QueryWrapper<RoleDO> queryWrapper1 = new QueryWrapper();
        List<RoleDO> roleList = roleMapper.findRoleListByUserId(userDO.getId());
        userDO.setRoleDOList(roleList);
        return userDO;
    }

    @Override
    public UserDO findSimpleUserInfoById(int userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public UserDO findSimpleUserInfoByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<UserDO>().eq("username",username));
    }
}
