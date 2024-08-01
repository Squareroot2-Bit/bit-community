package com.example.bitcommunity.service.score;

import com.example.bitcommunity.json.body.CreateRatedItemReviewBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.pojo.score.RatedItemReviewWithBrowser;

import java.util.List;

/**
 * @ClassName RatedItemReviewService
 * @Description
 * @date 2023/12/23 5:19
 * @Author Squareroot_2
 */
public interface RatedItemReviewService {
    /**
     * @param rated_item_id
     * @param pageBody
     * @return 获取该RatedItem下的review，按回复时间从早到晚排列
     */
    List<RatedItemReviewWithBrowser> getReviewByRatedItem(int rated_item_id, PageBody pageBody);

    List<RatedItemReviewWithBrowser> getReviewByToReview(
            int to_review_id);

    /**
     * @param review_id
     * @return 根据id获取review
     */
    RatedItemReviewWithBrowser getReviewById(int review_id);

    /**
     * @param createRatedItemReviewBody
     * @return  新建评论
     *          >0 成功
     *          -1 用户未登录
     */
    Integer addReview(CreateRatedItemReviewBody createRatedItemReviewBody);

    /**
     * @param review_id
     * @return 删除评论
     *          >0 成功
     *          -1 用户未登录
     *          -2 该Review不存在
     *          -3 用户无权限删除
     */
    Integer deleteReview(int review_id);

    /**
     * @param review_id
     * @return 给Review点赞
     *          >0 成功
     *          -1 用户未登录
     *          -2 该Review不存在
     *          -3 重复点赞
     */
    Integer likeTheReview(int review_id);

    /**
     * @param review_id
     * @return 取消对Review的点赞
     *          >0 成功
     *          -1 用户未登录
     *          -2 该Review不存在
     *          -3 重复取消点赞
     */
    Integer unlikeTheReview(int review_id);
}
