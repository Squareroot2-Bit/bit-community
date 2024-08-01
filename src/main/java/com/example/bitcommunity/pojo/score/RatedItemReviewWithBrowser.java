package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.rmi.server.UID;

/**
 * @ClassName RatedItemReviewWithBrowser
 * @Description
 * @date 2023/12/20 10:11
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemReviewWithBrowser {
    private RatedItemReviewWithReviewer ratedItemReviewWithReviewer;
    private UserBrief browser;
    private boolean is_liked;

    public RatedItemReviewWithBrowser(
            RatedItemReviewWithReviewer ratedItemReviewWithReviewer,
            UserInfo browser,
            RatedItemReviewLike reviewLike) {
        this(ratedItemReviewWithReviewer,
                new UserBrief(browser),
                reviewLike != null);
    }

    public RatedItemReviewWithBrowser(
            RatedItemReviewWithReviewer ratedItemReviewWithReviewer) {
        this(ratedItemReviewWithReviewer,
                null,
                false);
    }
}
