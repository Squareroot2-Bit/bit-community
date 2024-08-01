package com.example.bitcommunity.service.impl;

import com.example.bitcommunity.json.body.LoginAndRegisterBody;
import com.example.bitcommunity.json.data.UserBriefData;
import com.example.bitcommunity.json.data.UserBriefTokenData;
import com.example.bitcommunity.mapper.UserInfoMapper;
import com.example.bitcommunity.mapper.UserMapper;
import com.example.bitcommunity.pojo.UserAndUserInfo;
import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bitcommunity.pojo.User;
import com.example.bitcommunity.service.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public Boolean insert(User user) {
        return userMapper.insert(user) == 1 ? true : false;
    }

    @Override
    public Boolean delete(Integer uid) {
        return userMapper.delete(uid) == 1 ? true : false;
    }

    @Override
    public Boolean update(User user) {
//        return userMapper.update(user) == 1 ? true : false;
        return false;
    }


    @Override
    @Transactional
    public UserBriefTokenData register(LoginAndRegisterBody req) {
        // 查询bitid是否已经存在，如果存在返回null
        if(userMapper.selectByBitid(req.getBitid()) != null) {
            return null;
        }
        System.out.println("+++++"+req.getBitid());
        // 在user表中新增一行
        User user = new User();
        user.setBitid(req.getBitid());
        user.setPassword(req.getPassword());
        user.setCreate_time(LocalDateTime.now());
        user.setUpdate_time(LocalDateTime.now());
        user.setLast_login_time(LocalDateTime.now());
        if(userMapper.insert(user) != 1) {
            return null;
        }
        // 在userinfo表中新增一行
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(user.getUid());
        userInfo.setNickname(getRandomNickname());
        userInfo.setAvatar_url(getRandomAvatar());
        if(userInfoMapper.insert(userInfo) != 1) {
            return null;
        }
        // 生成jwt令牌
        Map<String, Object> chaims = new HashMap<>();
        chaims.put("uid", userInfo.getUid());
        String token = JwtUtil.generateJwt(chaims);
        // 按照返回格式生成返回格式数据
        UserBriefTokenData res = new UserBriefTokenData();
        res.setUid(userInfo.getUid());
        res.setNickname(userInfo.getNickname());
        res.setAvatar(userInfo.getAvatar_url());
        res.setToken(token);
        return res;
    }

    @Override
    public UserBriefTokenData login(LoginAndRegisterBody req) {
        // 判断user表中是否存在用户
        User user = userMapper.selectByBitidAndPassword(req.getBitid(), req.getPassword());
        if(user == null) {
            return null;
        }
        // 对user和userinfo表进行连接
        UserAndUserInfo userAndUserInfo = userMapper.selectByUid(user.getUid());
        // 生成jwt令牌
        Map<String, Object> chaims = new HashMap<>();
        chaims.put("uid", userAndUserInfo.getUid());
        String token = JwtUtil.generateJwt(chaims);
        // 按照返回格式生成返回格式数据
        UserBriefTokenData res = new UserBriefTokenData();
        res.setUid(userAndUserInfo.getUid());
        res.setNickname(userAndUserInfo.getNickname());
        res.setAvatar(userAndUserInfo.getAvatar_url());
        res.setToken(token);
        return res;
    }

    private String getRandomNickname() {
        String prefix = "测试用户";
        int suffixLength = 6;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(prefix);
        Random random = new Random();
        for (int i = 0; i < suffixLength; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private String getRandomAvatar() {
        String url = "https://bitcommunity-test.oss-cn-beijing.aliyuncs.com/default/avatar/%d.jpg";
        int index = new Random().nextInt(1, 16);
        return String.format(url, index);
    }
}
