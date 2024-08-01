package com.example.bitcommunity.service.score.impl;

import com.example.bitcommunity.json.body.CreateRatedItemBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.mapper.UserInfoMapper;
import com.example.bitcommunity.mapper.score.RatedItemMapper;
import com.example.bitcommunity.mapper.score.RatedItemReviewMapper;
import com.example.bitcommunity.mapper.score.ScoreInfoMapper;
import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.score.*;
import com.example.bitcommunity.service.TokenMessage;
import com.example.bitcommunity.service.score.RatedItemService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Mapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName RatedItemServiceImpl
 * @Description
 * @date 2023/12/23 3:26
 * @Author Squareroot_2
 */
@Service
public class RatedItemServiceImpl implements RatedItemService {

    @Autowired
    private RatedItemMapper ratedItemMapper;
    @Autowired
    private RatedItemReviewMapper ratedItemReviewMapper;
    @Autowired
    private ScoreInfoMapper scoreInfoMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * @param topic_id
     * @param pageBody
     * @return 获取该topic下的RatedItem，按评分人数从大到小排列
     */
    @Override
    public List<RatedItemWithBrowser> getRatedItemByTopic(
            int topic_id, PageBody pageBody) {
        List<RatedItem> ratedItemList = ratedItemMapper.selectByTopic(
                topic_id, pageBody.getOffset(), pageBody.getLimit());
        UserInfo browser = getUserInfoByToken();
        return ratedItemList.stream()
                .map(ratedItem -> getRatedItemWithBrowser(ratedItem, browser))
                .toList();
    }

    /**
     * @param rated_item_id
     * @return 根据id值获取RatedItem
     */
    @Override
    public RatedItemWithBrowser getRatedItemById(int rated_item_id) {
        RatedItem ratedItem = ratedItemMapper.selectByIdFromUndeleted(rated_item_id);
        UserInfo browser = getUserInfoByToken();
        return getRatedItemWithBrowser(ratedItem, browser);
    }

    /**
     * @param keyword
     * @param pageBody
     * @return 通过搜索获取RatedItem
     */
    @Override
    public List<RatedItemWithBrowser> selectRatedItem(String keyword, PageBody pageBody) {
        List<RatedItem> ratedItemList = ratedItemMapper.selectForSearch(
                keyword, pageBody.getOffset(), pageBody.getLimit());
        UserInfo browser = getUserInfoByToken();
        return ratedItemList.stream()
                .map(ratedItem -> getRatedItemWithBrowser(ratedItem, browser))
                .toList();
    }

    /**
     * @param createRatedItemBody
     * @return 添加RatedItem
     * >0 成功
     * -1 用户未登录
     */
    @Override
    public Integer addRatedItem(CreateRatedItemBody createRatedItemBody) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItem ratedItem = new RatedItem(createRatedItemBody, uid);
        return ratedItemMapper.insert(ratedItem);
    }

    /**
     * @param rated_item_id
     * @return 删除RatedItem
     * >0 成功
     * -1 用户未登录
     * -2 该RatedItem不存在
     * -3 用户无权限删除
     */
    @Override
    public Integer deleteRatedItem(int rated_item_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItem ratedItem = ratedItemMapper.selectByIdFromAll(rated_item_id);
        if (ratedItem == null || ratedItem.getIs_deleted() == 1)
            return -2;
        int publishUid = ratedItem.getUid();
        if (publishUid != uid) return -3;
        return ratedItemMapper.delete(rated_item_id);
    }

    /**
     * @param rated_item_id
     * @param score
     * @return 用户给RatedItem添加评分
     * >0 成功
     * -1 用户未登录
     * -2 该RatedItem不存在
     * -3 score不合法或scoreInfo记录已存在
     */
    @Override
    public Integer addScoreForRatedItem(int rated_item_id, int score) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        RatedItem ratedItem = ratedItemMapper.selectByIdFromAll(rated_item_id);
        if (ratedItem == null || ratedItem.getIs_deleted() == 1)
            return -2;
        ScoreInfo scoreInfo = scoreInfoMapper.selectScore(rated_item_id, uid);
        if (scoreInfo != null) return -3;
        ScoreChanger scoreAdder = addScore(score);
        if (scoreAdder == null) return -3;

        scoreInfo = new ScoreInfo(rated_item_id, uid, score);
        int result = scoreInfoMapper.insert(scoreInfo);
        if (result > 0) scoreAdder.changeScore(rated_item_id);
        return result;
    }

    /**
     * @param rated_item_id
     * @param score
     * @return 用户修改对RatedItem的评分
     * >0 成功
     * -1 用户未登录
     * -2 该ScoreInfo不存在
     * -3 score不合法
     */
    @Override
    public Integer updateScoreForRatedItem(int rated_item_id, int score) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        ScoreInfo scoreInfo = scoreInfoMapper.selectScore(rated_item_id, uid);
        if (scoreInfo == null) return -2;
        if (scoreInfo.getScore() == score) return -3;
        ScoreChanger scoreAdder = addScore(score);
        if (scoreAdder == null) return -3;
        ScoreChanger scoreDeleter = deleteScore(scoreInfo.getScore());
        if (scoreDeleter == null) return -3;

        scoreInfo = new ScoreInfo(rated_item_id, uid, score);
        int result = scoreInfoMapper.update(scoreInfo);
        if (result > 0) {
            scoreDeleter.changeScore(rated_item_id);
            scoreAdder.changeScore(rated_item_id);
        }
        return result;
    }

    /**
     * @param rated_item_id
     * @return 用户修改对RatedItem的评分
     * >0 成功
     * -1 用户未登录
     * -2 该ScoreInfo不存在
     */
    @Override
    public Integer deleteScoreForRatedItem(int rated_item_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        ScoreInfo scoreInfo = scoreInfoMapper.selectScore(rated_item_id, uid);
        if (scoreInfo == null) return -2;
        ScoreChanger scoreDeleter = deleteScore(scoreInfo.getScore());
        if (scoreDeleter == null) return -3;

        int result = scoreInfoMapper.delete(rated_item_id, uid);
        if (result > 0) scoreDeleter.changeScore(rated_item_id);
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
        return uid < 0 ? null : userInfoMapper.selectByUid(uid);
    }

    /**
     * @param ratedItem
     * @param browser
     * @return 根据RatedItem, 生成RatedItemWithBrowser
     */
    @NotNull
    private RatedItemWithBrowser getRatedItemWithBrowser(
            RatedItem ratedItem, UserInfo browser) {
        UserInfo publisher = userInfoMapper.selectByUid(ratedItem.getUid());
        List<RatedItemReview> reviews = ratedItemReviewMapper
                .selectTheBestOfRatedItem(ratedItem.getRated_item_id());
        RatedItemReview bestReview = reviews.isEmpty() ? null : reviews.get(0);
        RatedItemWithPublisher ratedItemWithPublisher =
                new RatedItemWithPublisher(ratedItem, publisher, bestReview);
        if (browser == null) {
            return new RatedItemWithBrowser(ratedItemWithPublisher);
        } else {
            ScoreInfo scoreInfo = scoreInfoMapper
                    .selectScore(ratedItem.getRated_item_id(), browser.getUid());
            return new RatedItemWithBrowser(
                    ratedItemWithPublisher, browser, scoreInfo);
        }
    }

    @FunctionalInterface
    private interface ScoreChanger {
        int changeScore(Integer rated_item_id);
    }

    private ScoreChanger addScore(int score) {
        return switch (score) {
            case 2 -> ratedItemMapper::addScore2;
            case 4 -> ratedItemMapper::addScore4;
            case 6 -> ratedItemMapper::addScore6;
            case 8 -> ratedItemMapper::addScore8;
            case 10 -> ratedItemMapper::addScore10;
            default -> null;
        };
    }

    private ScoreChanger deleteScore(int score) {
        return switch (score) {
            case 2 -> ratedItemMapper::deleteScore2;
            case 4 -> ratedItemMapper::deleteScore4;
            case 6 -> ratedItemMapper::deleteScore6;
            case 8 -> ratedItemMapper::deleteScore8;
            case 10 -> ratedItemMapper::deleteScore10;
            default -> null;
        };
    }
}
