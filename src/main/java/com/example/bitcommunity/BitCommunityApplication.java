package com.example.bitcommunity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.bitcommunity.mapper")
public class BitCommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(BitCommunityApplication.class, args);
    }
}
