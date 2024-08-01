package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.RatedItemWithBrowser;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RatedItemDetailData
 * @Description
 * @date 2023/12/30 20:44
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemDetailData {
    private int topic_id;
    private int rated_item_id;
    private String name;
    private String image;
    private String description;
    private UserBriefData user;
    private int score_by_user;
    private ScoreData scores;

    public RatedItemDetailData(RatedItemWithBrowser ratedItem) {
        this.topic_id = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getTopic_id();
        this.rated_item_id = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getRated_item_id();
        this.name = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getName();
        this.image = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getImage();
        this.description = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getDescription();
        this.user = new UserBriefData(ratedItem.getRatedItemWithPublisher()
                .getPublisher());
        this.score_by_user = ratedItem.getScore();
        this.scores = new ScoreData(ratedItem);
    }
}
