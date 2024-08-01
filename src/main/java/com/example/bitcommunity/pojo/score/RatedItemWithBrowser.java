package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RatedItemWithBrowser
 * @Description 浏览者视角的评分项
 * @date 2023/12/19 18:02
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemWithBrowser {
    private RatedItemWithPublisher ratedItemWithPublisher;
    private UserBrief browser;  //浏览者的个人信息
    private int score;          //浏览者对该评分项的评分

    public RatedItemWithBrowser(
            RatedItemWithPublisher ratedItemWithPublisher,
            UserInfo browser,
            ScoreInfo scoreInfo) {
        this.ratedItemWithPublisher = ratedItemWithPublisher;
        this.browser = new UserBrief(browser);
        this.score = (scoreInfo == null) ? 0 : scoreInfo.getScore();
    }

    //未登录用户看到的
    public RatedItemWithBrowser(
            RatedItemWithPublisher ratedItemWithPublisher) {
        this.ratedItemWithPublisher = ratedItemWithPublisher;
        this.browser = null;
        this.score = 0;
    }
}
