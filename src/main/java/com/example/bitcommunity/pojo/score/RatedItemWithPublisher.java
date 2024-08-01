package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName RatedItemWithPublisher
 * @Description
 * @date 2023/12/19 2:16
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemWithPublisher {
    private RatedItem ratedItem;    //评分项信息
    private UserBrief publisher;    //发布者的个人信息
    private String best_review;     //最佳评论

    public RatedItemWithPublisher(RatedItem ratedItem, UserInfo publisher, RatedItemReview best_review) {
        this(ratedItem, new UserBrief(publisher), best_review == null ? "" : best_review.getReview());
    }

    public RatedItemWithPublisher(RatedItem ratedItem) {
        this(ratedItem, (UserBrief) null, null);
    }

/*    public RatedItemWithPublisher(RatedItem ratedItem, UserInfo publisher) {
        this(ratedItem.getRated_item_id(),
                ratedItem.getTopic_id(),
                ratedItem.getName(),
                ratedItem.getImage(),
                ratedItem.getDescription(),
                ratedItem.getUid(),
                ratedItem.getPeople_10(),
                ratedItem.getPeople_8(),
                ratedItem.getPeople_6(),
                ratedItem.getPeople_4(),
                ratedItem.getPeople_2(),
                ratedItem.getCreate_time(),
                ratedItem.getUpdate_time(),
                ratedItem.getIs_deleted(),
                publisher.getNickname(),
                publisher.getAvatar_url(),
                publisher.getGender());
    }*/
}
