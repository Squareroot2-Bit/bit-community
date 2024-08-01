package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.RatedItemWithBrowser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RatedItemData
 * @Description
 * @date 2023/12/23 17:24
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemData {
    private int rated_item_id;
    private String name;
    private String img;
    private String description;
    private int uid;
    private String max_likes_review;
    double score;
    private int people_scored;
    private int score_by_user;

    public RatedItemData(RatedItemWithBrowser ratedItemWithBrowse) {
        this.rated_item_id = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getRated_item_id();
        this.name = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getName();
        this.img = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getImage();
        this.description = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getDescription();
        this.uid = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getUid();
        this.max_likes_review = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getBest_review();
        this.score = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getAvg_score();
        this.people_scored = ratedItemWithBrowse
                .getRatedItemWithPublisher()
                .getRatedItem()
                .getPeople_sum();
        this.score_by_user = ratedItemWithBrowse.getScore();
    }
}
