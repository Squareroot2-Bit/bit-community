package com.example.bitcommunity.service;

import com.example.bitcommunity.pojo.User;
import com.example.bitcommunity.pojo.UserInfo;

public interface UserInfoService {
    UserInfo selectByUid(Integer uid);

    Boolean insert(UserInfo userInfo);

    Boolean delete(Integer uid);

    Boolean update(UserInfo userInfo);
}
