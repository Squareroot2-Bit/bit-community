package com.example.bitcommunity.service;

import com.example.bitcommunity.json.body.LoginAndRegisterBody;
import com.example.bitcommunity.json.data.UserBriefData;
import com.example.bitcommunity.json.data.UserBriefTokenData;
import com.example.bitcommunity.pojo.User;

public interface UserService {
    Boolean insert(User user);

    Boolean delete(Integer uid);

    Boolean update(User user);

    UserBriefTokenData register(LoginAndRegisterBody req);

    UserBriefTokenData login(LoginAndRegisterBody req);
}
