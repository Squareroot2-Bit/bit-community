package com.example.bitcommunity.service.impl;

import com.example.bitcommunity.mapper.administrator.AdministratorMapper;
import com.example.bitcommunity.pojo.administrator.Administrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @ClassName AdministratorService
 * @Description
 * @date 2023/12/29 1:33
 * @Author Squareroot_2
 */
@Service
public class AdministratorService implements UserDetailsService {

    @Autowired
    AdministratorMapper administratorMapper;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        //调用AdministratorMapper查询数据库
        Administrator administrator = administratorMapper.selectByName(name);
        if (administrator == null) {
            throw new RuntimeException("管理员用户名不存在！");
        }
        else return administrator;
    }
}
