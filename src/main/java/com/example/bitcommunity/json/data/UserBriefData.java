package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserBriefData
 * @Description
 * @date 2023/11/26 0:45
 * @Author Squareroot_2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBriefData {
    private String avatar;
    private String nickname;
    private Integer uid;

    public UserBriefData(UserInfo userInfo) {
        this(userInfo.getAvatar_url(), userInfo.getNickname(), userInfo.getUid());
    }

    public UserBriefData(UserBrief userBrief) {
        this.avatar = userBrief.getAvatar_url();
        this.nickname = userBrief.getNickname();
        this.uid = userBrief.getUid();
    }
}
