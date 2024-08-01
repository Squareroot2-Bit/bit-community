package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.score.RatedItemReviewWithBrowser;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName RatedItemReviewData
 * @Description
 * @date 2023/12/26 21:06
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemReviewData {
    private int review_id;
    private UserBriefData reviewer;
    private String content;
    private int score;
    private int likes;
    private Boolean isLiked;
    private LocalDateTime createTime;
    private List<SubRatedItemReviewData> subReviews;
    private int totalSubReviews;

    public RatedItemReviewData(
            RatedItemReviewWithBrowser review,
            List<RatedItemReviewWithBrowser> subReviews
    ) {
        this.review_id = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getReview_id();
        this.reviewer = new UserBriefData(review
                .getRatedItemReviewWithReviewer()
                .getReviewer());
        this.content = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getReview();
        this.score = review.getRatedItemReviewWithReviewer().getReviewer_score();
        this.likes = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getLikes();
        this.isLiked = review.is_liked();
        this.createTime = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getCreate_time();
        this.subReviews = subReviews.stream()
                .map(SubRatedItemReviewData::new)
                .toList();
        this.totalSubReviews = subReviews.size();
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SubRatedItemReviewData {
    private String content;
    private LocalDateTime create_time;
    private Boolean is_liked;    //是否点赞
    private Integer likes;
    private Integer review_id;
    private UserBriefData reviewer;
    private RatedItemReviewTargetData target;      //回复给谁的评论

    public SubRatedItemReviewData(RatedItemReviewWithBrowser review) {
        this.content = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getReview();
        this.create_time = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getCreate_time();
        this.is_liked = review.is_liked();
        this.likes = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getLikes();
        this.review_id = review.getRatedItemReviewWithReviewer()
                .getRatedItemReview()
                .getReview_id();
        this.reviewer = new UserBriefData(review
                .getRatedItemReviewWithReviewer()
                .getReviewer());
        this.target = new RatedItemReviewTargetData(
                review.getRatedItemReviewWithReviewer()
                        .getRatedItemReview()
                        .getReview_id(),
                review.getRatedItemReviewWithReviewer()
                        .getReviewer()
        );
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RatedItemReviewTargetData {
    private Integer review_id;
    private UserBriefData reviewer;

    public RatedItemReviewTargetData(Integer review_id, UserBrief reviewer) {
        this.review_id = review_id;
        this.reviewer = new UserBriefData(reviewer);
    }
}