package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RatedItemReviewWithReviewer
 * @Description
 * @date 2023/12/20 10:01
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemReviewWithReviewer {
    private RatedItemReview ratedItemReview;
    private UserBrief reviewer;
    private int reviewer_score;

    public RatedItemReviewWithReviewer(
            RatedItemReview ratedItemReview,
            UserInfo reviewer,
            ScoreInfo reviewer_score) {
        this(ratedItemReview, new UserBrief(reviewer),
                reviewer_score == null ? 0 : reviewer_score.getScore());
    }
}
