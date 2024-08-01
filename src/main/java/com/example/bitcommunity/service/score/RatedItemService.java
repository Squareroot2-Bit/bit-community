package com.example.bitcommunity.service.score;

import com.example.bitcommunity.json.body.CreateRatedItemBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.pojo.score.RatedItemWithBrowser;

import java.util.List;

/**
 * @ClassName RatedItemService
 * @Description
 * @date 2023/12/23 2:10
 * @Author Squareroot_2
 */
public interface RatedItemService {
    /**
     * @param topic_id
     * @param pageBody
     * @return 获取该topic下的RatedItem，按评分人数顺序排列
     */
    List<RatedItemWithBrowser> getRatedItemByTopic(int topic_id, PageBody pageBody);

    /**
     * @param rated_item_id
     * @return 根据id值获取RatedItem
     */
    RatedItemWithBrowser getRatedItemById(int rated_item_id);

    /**
     * @param keyword
     * @param pageBody
     * @return 通过搜索获取RatedItem
     */
    List<RatedItemWithBrowser> selectRatedItem(String keyword, PageBody pageBody);

    /**
     * @param createRatedItemBody
     * @return 添加RatedItem
     *         >0 成功
     *         -1 用户未登录
     */
    Integer addRatedItem(CreateRatedItemBody createRatedItemBody);

    /**
     * @param rated_item_id
     * @return 删除RatedItem
     *         >0 成功
     *         -1 用户未登录
     *         -2 该RatedItem不存在
     *         -3 用户无权限删除
     */
    Integer deleteRatedItem(int rated_item_id);

    /**
     * @param rated_item_id
     * @param score
     * @return 用户给RatedItem添加评分
     *         >0 成功
     *         -1 用户未登录
     *         -2 该RatedItem不存在
     *         -3 score不合法
     */
    Integer addScoreForRatedItem(int rated_item_id, int score);

    /**
     * @param rated_item_id
     * @param score
     * @return 用户修改对RatedItem的评分
     *         >0 成功
     *         -1 用户未登录
     *         -2 该ScoreInfo不存在
     *         -3 score不合法
     */
    Integer updateScoreForRatedItem(int rated_item_id, int score);

    /**
     * @param rated_item_id
     * @return 用户修改对RatedItem的评分
     *         >0 成功
     *         -1 用户未登录
     *         -2 该ScoreInfo不存在
     */
    Integer deleteScoreForRatedItem(int rated_item_id);
}
