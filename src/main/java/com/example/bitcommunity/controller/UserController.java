package com.example.bitcommunity.controller;

import com.example.bitcommunity.json.Result;
import com.example.bitcommunity.json.body.LoginAndRegisterBody;
import com.example.bitcommunity.json.data.UserBriefTokenData;
import com.example.bitcommunity.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody LoginAndRegisterBody req) {
//        System.out.println("99999"+req.toString());
        UserBriefTokenData res = userService.register(req);
        return res != null ? Result.success(res) : Result.error("该用户已注册");
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginAndRegisterBody req) {
        UserBriefTokenData res = userService.login(req);
        return res != null ? Result.success(res) : Result.error("学工号/邮箱或密码错误");
    }
}
