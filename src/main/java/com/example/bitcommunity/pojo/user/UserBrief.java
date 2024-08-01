package com.example.bitcommunity.pojo.user;

import com.example.bitcommunity.pojo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserBrief
 * @Description
 * @date 2023/12/19 18:08
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBrief {
    private int uid;
    private String nickname;  //发布者昵称
    private String avatar_url;//发布者头像url
    private int gender;

    public UserBrief(UserInfo userInfo) {
        this(userInfo.getUid(),
                userInfo.getNickname(),
                userInfo.getAvatar_url(),
                userInfo.getGender());
    }
}
