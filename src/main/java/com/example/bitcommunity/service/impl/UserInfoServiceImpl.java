package com.example.bitcommunity.service.impl;

import com.example.bitcommunity.mapper.UserInfoMapper;
import com.example.bitcommunity.mapper.UserMapper;
import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo selectByUid(Integer uid) {
        return userInfoMapper.selectByUid(uid);
    }

    @Override
    public Boolean insert(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo) == 1 ? true : false;
    }

    @Override
    public Boolean delete(Integer uid) {
        return userInfoMapper.delete(uid) == 1 ? true : false;
    }

    @Override
    public Boolean update(UserInfo userInfo) {
        return userInfoMapper.update(userInfo) == 1 ? true : false;
    }
}
