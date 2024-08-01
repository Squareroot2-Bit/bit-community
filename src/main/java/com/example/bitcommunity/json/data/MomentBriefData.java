package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.Moment;
import com.example.bitcommunity.pojo.sql.MomentForBrowse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName MomentBriefData
 * @Description
 * @date 2023/11/26 0:48
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
public class MomentBriefData {
    private String cover_image;
    private Integer type;
    private Boolean is_liked;//用户是否点赞
    private Integer likes;     //点赞数
    private Integer momentid;
    private String title;
    private UserBriefData user;

    public MomentBriefData(Moment moment, boolean isLiked, UserBriefData user) {
        this(moment.getCover_image_url(),moment.getType(), isLiked, moment.getLikes(),
                moment.getMid(), moment.getTitle(), user);
    }

    public MomentBriefData(MomentForBrowse m) {
        this(m.getCover_image_url(),m.getType(), m.getIs_liked(), m.getLikes(), m.getMid(), m.getTitle(),
                new UserBriefData(m.getAvatar_url(), m.getNickname(), m.getUid()));
    }
}
