package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.RatedItemWithPublisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemBriefData {
    private long rated_item_id;
    private String name;
    private String image;
    private String description;
    /**
     * 创建该评分项的人的uid
     */
    private long uid;
    /**
     * 该评分项最高点赞数评论的内容
     */
    private String max_likes_review;
    /**
     * 【保留一位小数】该评分项的总分数，如9.6
     */
    private double score;
    /**
     * 该评分项的打分人数
     */
    private long people_scored;

    /*public RatedItemBriefData(RatedItemWithBrowser ratedItem) {
        this.ratedItemid = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getRated_item_id();
        this.name = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getName();
        this.img = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getImage();
        this.description = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getDescription();
        this.uid = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getUid();
        this.maxLikesReview = ratedItem.getRatedItemWithPublisher()
                .getBest_review();
        this.score = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getAvg_score();
        this.peopleScored = ratedItem.getRatedItemWithPublisher()
                .getRatedItem()
                .getPeople_sum();

    }*/

    public RatedItemBriefData(RatedItemWithPublisher ratedItem) {
        this.rated_item_id = ratedItem
                .getRatedItem()
                .getRated_item_id();
        this.name = ratedItem
                .getRatedItem()
                .getName();
        this.image = ratedItem
                .getRatedItem()
                .getImage();
        this.description = ratedItem
                .getRatedItem()
                .getDescription();
        this.uid = ratedItem
                .getRatedItem()
                .getUid();
        this.max_likes_review = ratedItem
                .getBest_review();
        this.score = ratedItem
                .getRatedItem()
                .getAvg_score();
        this.people_scored = ratedItem
                .getRatedItem()
                .getPeople_sum();
    }
}