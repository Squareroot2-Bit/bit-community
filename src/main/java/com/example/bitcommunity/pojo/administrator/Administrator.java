package com.example.bitcommunity.pojo.administrator;

import com.example.bitcommunity.authority.AdminAuthority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @ClassName Administrator
 * @Description
 * @date 2023/12/19 23:38
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator implements UserDetails {
    private int admin_id;
    private String name;
    private String password;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private int is_deleted;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(AdminAuthority.getInstance());
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
