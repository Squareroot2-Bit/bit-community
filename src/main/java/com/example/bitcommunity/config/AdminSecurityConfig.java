package com.example.bitcommunity.config;

import com.example.bitcommunity.service.impl.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * @ClassName AdminSecurityConfig
 * @Description
 * @date 2023/12/28 23:32
 * @Author Squareroot_2
 */
@Configuration
public class AdminSecurityConfig {
    @Autowired
    AdministratorService administratorService;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        httpSecurity.authorizeHttpRequests(request -> {
            request.requestMatchers("/login",
                            "/moments/**","/upload/**",
                            "/users/**","/topics/**")
                    .permitAll().
                    requestMatchers("/admin/manager/**").hasRole("ADMIN");
        });
        httpSecurity.formLogin(request -> {
            request.defaultSuccessUrl("/admin/manager", true);
        });
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return
    }*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
