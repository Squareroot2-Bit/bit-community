package com.example.bitcommunity.service.score.impl;

import com.example.bitcommunity.json.body.CreateRatedItemReviewBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.mapper.UserInfoMapper;
import com.example.bitcommunity.mapper.score.RatedItemReviewLikeMapper;
import com.example.bitcommunity.mapper.score.RatedItemReviewMapper;
import com.example.bitcommunity.mapper.score.ScoreInfoMapper;
import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.score.*;
import com.example.bitcommunity.service.TokenMessage;
import com.example.bitcommunity.service.score.RatedItemReviewService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RatedItemReviewServiceImpl
 * @Description
 * @date 2023/12/23 7:40
 * @Author Squareroot_2
 */
@Service
public class RatedItemReviewServiceImpl implements RatedItemReviewService {

    @Autowired
    private RatedItemReviewMapper ratedItemReviewMapper;
    @Autowired
    private RatedItemReviewLikeMapper ratedItemReviewLikeMapper;
    @Autowired
    private ScoreInfoMapper scoreInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * @param rated_item_id
     * @param pageBody
     * @return 获取该RatedItem下的review，按回复时间从早到晚排列
     */
    @Override
    public List<RatedItemReviewWithBrowser> getReviewByRatedItem(
            int rated_item_id, PageBody pageBody) {
        List<RatedItemReview> ratedItemReviewList =
                ratedItemReviewMapper.selectByRatedItem(
                        rated_item_id,
                        pageBody.getOffset(),
                        pageBody.getLimit());
        UserInfo browser = getUserInfoByToken();
        return ratedItemReviewList.stream()
                .map(review -> getRatedItemReviewWithBrowser(review, browser))
                .toList();
    }

    @Override
    public List<RatedItemReviewWithBrowser> getReviewByToReview(
            int to_review_id) {
        List<RatedItemReview> ratedItemReviewList =
                ratedItemReviewMapper.selectByToReview(to_review_id);
        UserInfo browser = getUserInfoByToken();
        return ratedItemReviewList.stream()
                .map(review -> getRatedItemReviewWithBrowser(review, browser))
                .toList();
    }

    /**
     * @param review_id
     * @return 根据id获取review
     */
    @Override
    public RatedItemReviewWithBrowser getReviewById(int review_id) {
        RatedItemReview review =
                ratedItemReviewMapper.selectByIdFromUndeleted(review_id);
        UserInfo browser = getUserInfoByToken();
        return getRatedItemReviewWithBrowser(review, browser);
    }

    /**
     * @param createRatedItemReviewBody
     * @return 新建评论
     * >0 成功
     * -1 用户未登录
     */
    @Override
    public Integer addReview(CreateRatedItemReviewBody createRatedItemReviewBody) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        if (createRatedItemReviewBody.getTo_review_id() <= 0) {
            createRatedItemReviewBody.setTo_review_id(-1);
            RatedItemReview ratedItemReview =
                    new RatedItemReview(
                            createRatedItemReviewBody, uid, -1, -1);
            return ratedItemReviewMapper.insert(ratedItemReview);
        } else {
            RatedItemReview superiorReview =
                    ratedItemReviewMapper.selectByIdFromAll(
                            createRatedItemReviewBody.getTo_review_id());
            if (superiorReview == null) {
                createRatedItemReviewBody.setTo_review_id(-1);
                RatedItemReview ratedItemReview =
                        new RatedItemReview(
                                createRatedItemReviewBody, uid, -1, -1);
                return ratedItemReviewMapper.insert(ratedItemReview);
            } else {
                int to_uid = superiorReview.getUid();
                int directReviewId = superiorReview.getDirect_review_id() == -1 ?
                        superiorReview.getReview_id() :
                        superiorReview.getDirect_review_id();
                RatedItemReview ratedItemReview =
                        new RatedItemReview(createRatedItemReviewBody,
                                uid, to_uid, directReviewId);
                return ratedItemReviewMapper.insert(ratedItemReview);
            }
        }
    }

    /**
     * @param review_id
     * @return 删除评论
     * >0 成功
     * -1 用户未登录
     * -2 该Review不存在
     * -3 用户无权限删除
     */
    @Override
    public Integer deleteReview(int review_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItemReview review =
                ratedItemReviewMapper.selectByIdFromAll(review_id);
        if (review == null || review.getIs_deleted() == 1)
            return -2;
        int reviewerUid = review.getUid();
        if (reviewerUid == uid) return -3;
        return ratedItemReviewMapper.delete(review_id);
    }

    /**
     * @param review_id
     * @return 给Review点赞
     * >0 成功
     * -1 用户未登录
     * -2 该Review不存在
     * -3 重复点赞
     */
    @Override
    public Integer likeTheReview(int review_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItemReview review =
                ratedItemReviewMapper.selectByIdFromAll(review_id);
        if (review == null || review.getIs_deleted() == 1)
            return -2;
        RatedItemReviewLike reviewLike = ratedItemReviewLikeMapper.select(
                review_id, uid);
        if (reviewLike != null) return -3;

        int result = ratedItemReviewLikeMapper.insert(new RatedItemReviewLike(review_id, uid));
        if (result > 0)
            ratedItemReviewMapper.addLike(review_id);
        return result;
    }

    /**
     * @param review_id
     * @return 取消对Review的点赞
     * >0 成功
     * -1 用户未登录
     * -2 该Review不存在
     * -3 重复取消点赞
     */
    @Override
    public Integer unlikeTheReview(int review_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItemReview review =
                ratedItemReviewMapper.selectByIdFromAll(review_id);
        if (review == null || review.getIs_deleted() == 1)
            return -2;
        RatedItemReviewLike reviewLike = ratedItemReviewLikeMapper.select(
                review_id, uid);
        if (reviewLike != null) return -3;

        int result = ratedItemReviewLikeMapper.delete(new RatedItemReviewLike(review_id, uid));
        if (result > 0)
            ratedItemReviewMapper.deleteLike(review_id);
        return result;
    }

    /**
     * @return 从token中获取uid
     */
    private Integer getUidByToken() {
        return TokenMessage.getUidByToken(request);
    }

    /**
     * @return 从token中获取uid, 取得相应的UserInfo
     */
    private UserInfo getUserInfoByToken() {
        Integer uid = TokenMessage.getUidByToken(request);
        return uid <= 0 ? null : userInfoMapper.selectByUid(uid);
    }

    /**
     * @param review
     * @param browser
     * @return 根据RatedItemReview, 生成RatedItemReviewWithBrowser
     */
    @NonNull
    private RatedItemReviewWithBrowser getRatedItemReviewWithBrowser(
            RatedItemReview review, UserInfo browser) {
        UserInfo reviewer = userInfoMapper.selectByUid(review.getUid());
        ScoreInfo scoreInfo = scoreInfoMapper.selectScore(
                review.getRated_item_id(), review.getUid());
        RatedItemReviewWithReviewer ratedItemReviewWithReviewer =
                new RatedItemReviewWithReviewer(review, reviewer, scoreInfo);

        if (browser == null)
            return new RatedItemReviewWithBrowser(ratedItemReviewWithReviewer);
        else {
            RatedItemReviewLike reviewLike = ratedItemReviewLikeMapper.select(
                    review.getReview_id(), browser.getUid());
            return new RatedItemReviewWithBrowser(ratedItemReviewWithReviewer,
                    browser, reviewLike);
        }

    }
}
