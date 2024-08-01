package com.example.bitcommunity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private int uid;            //user id
    private String nickname;    //用户昵称
    private String avatar_url;  //用户头像url
    private int gender;         //用户性别
    private LocalDateTime birthday; //用户生日
    private String profile;     //个人简介（个性签名）
    private LocalDateTime create_time;      //创建时间
    private LocalDateTime update_time;      //修改时间
}
