package com.example.bitcommunity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomentAndUserInfo {
    private int mid;    //moment id
    private int uid;    //user's uid
    private String title;   //标题
    private int type;       //类型（暂时用不到，默认设0）
    private String content; //内容
    private String cover_image_url; //内容图片url
    private int likes;      //点赞数
    private LocalDateTime create_time;      //创建时间
    private LocalDateTime update_time;
    private String nickname;    //用户昵称
    private String avatar_url;  //用户头像url
}
