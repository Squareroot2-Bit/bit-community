package com.example.bitcommunity.authority;

import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName AdminAuthority
 * @Description
 * @date 2023/12/29 1:44
 * @Author Squareroot_2
 */
public class AdminAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "ROLE_ADMIN";
    }
    private static final AdminAuthority adminAuthority = new AdminAuthority();
    public static AdminAuthority getInstance() {
        return adminAuthority;
    }
}
