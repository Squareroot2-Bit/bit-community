package com.example.bitcommunity.controller;

import com.example.bitcommunity.json.Result;
import com.example.bitcommunity.json.body.*;
import com.example.bitcommunity.json.data.*;
import com.example.bitcommunity.pojo.score.RatedItemReviewWithBrowser;
import com.example.bitcommunity.pojo.score.RatedItemWithBrowser;
import com.example.bitcommunity.pojo.score.TopicWithPublisher;
import com.example.bitcommunity.service.TokenMessage;
import com.example.bitcommunity.service.score.RatedItemReviewService;
import com.example.bitcommunity.service.score.RatedItemService;
import com.example.bitcommunity.service.score.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TopicController
 * @Description
 * @date 2023/12/23 15:38
 * @Author Squareroot_2
 */
@RestController
@RequestMapping("/topics")
public class TopicController {
    @Autowired
    RatedItemReviewService ratedItemReviewService;
    @Autowired
    RatedItemService ratedItemService;
    @Autowired
    TopicService topicService;
    @Autowired
    private HttpServletRequest request;

    private static final int TopicDefaultOffset = 0;
    private static final int TopicDefaultLimit = 10;

    @GetMapping
    Result browseTopic(@RequestParam("offset") Integer offset,
                       @RequestParam("limit") Integer limit) {
        List<TopicWithPublisher> topicList =
                topicService.getTopicList(new PageBody(offset, limit));
        @Data
        @AllArgsConstructor
        class TopicDataList {
            List<TopicData> topics;
            int num;
        }
        if (topicList.isEmpty())
            return Result.success(new TopicDataList(new ArrayList<>(), 0));
        List<TopicData> topics = topicList.stream()
                .map(TopicData::new)
                .toList();

        return Result.success(new TopicDataList(topics, topics.size()));
    }

    @PostMapping
    Result createTopic(@RequestBody CreateTopicBody createTopicBody) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer topicId = topicService.addTopic(createTopicBody);
        if (topicId < 0) return Result.error("添加失败!");
        else {
            for (CreateRatedItemBody item : createTopicBody.getItems()) {
                item.setTopic_id(topicId);
                ratedItemService.addRatedItem(item);
            }
            return Result.success();
        }
    }

    @GetMapping("/{topic_id}")
    Result getTopic(@PathVariable("topic_id") int topic_id) {
        TopicWithPublisher topic = topicService.getTopicByIdFromValid(topic_id);
        if (topic != null) return Result.success(new TopicBriefData(topic));
        else return Result.error("该话题不存在！");
    }

    @GetMapping("/{topic_id}/items")
    Result browseRatedItem(@PathVariable("topic_id") int topic_id,
                           @RequestParam("offset") Integer offset,
                           @RequestParam("limit") Integer limit) {
        @Data
        @AllArgsConstructor
        class RatedItemDataList {
            List<RatedItemData> rated_items;
            int num;
        }
        List<RatedItemWithBrowser> ratedItemList =
                ratedItemService.getRatedItemByTopic(
                        topic_id, new PageBody(offset, limit));
        if (ratedItemList.isEmpty())
            return Result.success(new RatedItemDataList(new ArrayList<>(), 0));
        List<RatedItemData> rated_items =
                ratedItemList.stream().map(RatedItemData::new).toList();
        return Result.success(new RatedItemDataList(rated_items, ratedItemList.size()));
    }

    @PostMapping("/{topic_id}/items")
    Result createRatedItem(@PathVariable("topic_id") int topic_id,
                           @RequestBody CreateRatedItemBody createRatedItemBody) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        createRatedItemBody.setTopic_id(topic_id);
        Integer addedRatedItem = ratedItemService.addRatedItem(createRatedItemBody);
        if (addedRatedItem < 0) return Result.error("添加失败!");
        else return Result.success();
    }

    @PostMapping("/{topic_id}/items/{rated_item_id}")
    Result scoreRatedItem(@PathVariable("topic_id") Integer topic_id,
                          @PathVariable("rated_item_id") Integer rated_item_id,
                          @RequestBody ScoreBody score) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer scoreCreator = ratedItemService.addScoreForRatedItem(rated_item_id, score.getScore());
        if (scoreCreator < 0) return Result.error("添加失败!");
        else return Result.success();
    }

    @PutMapping("/{topic_id}/items/{rated_item_id}")
    Result changeScoreRatedItem(@PathVariable("topic_id") Integer topic_id,
                                @PathVariable("rated_item_id") Integer rated_item_id,
                                @RequestBody ScoreBody score) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer scoreChenger = ratedItemService.updateScoreForRatedItem(rated_item_id, score.getScore());
        if (scoreChenger < 0) return Result.error("修改失败!");
        else return Result.success();
    }

    @GetMapping("/{topic_id}/items/{rated_item_id}")
    Result getRatedItem(@PathVariable Integer topic_id,
                        @PathVariable Integer rated_item_id) {
        RatedItemWithBrowser ratedItem =
                ratedItemService.getRatedItemById(rated_item_id);
        if (ratedItem != null)
            return Result.success(new RatedItemDetailData(ratedItem));
        else return Result.error("该评分项不存在！");
    }

    @GetMapping("/{topic_id}/items/{rated_item_id}/reviews")
    Result browseRatedItemReview(@PathVariable int topic_id,
                                 @PathVariable("rated_item_id") int rated_item_id,
                                 @RequestParam("offset") Integer offset,
                                 @RequestParam("limit") Integer limit) {
        List<RatedItemReviewWithBrowser> reviewList = ratedItemReviewService.getReviewByRatedItem(rated_item_id,
                new PageBody(offset, limit));
        @Data
        @AllArgsConstructor
        class RatedItemReviewDataList {
            List<RatedItemReviewData> reviews;
            int num;
        }
        if (reviewList.isEmpty())
            return Result.success(new RatedItemReviewDataList(new ArrayList<>(), 0));
        List<RatedItemReviewData> reviews = reviewList.stream()
                .map(review -> {
                    List<RatedItemReviewWithBrowser> subReviewList =
                            ratedItemReviewService.getReviewByToReview(
                                    review.getRatedItemReviewWithReviewer()
                                            .getRatedItemReview()
                                            .getReview_id());
                    return new RatedItemReviewData(review, subReviewList);
                }).toList();

        return Result.success(new RatedItemReviewDataList(reviews, reviewList.size()));
    }

    @PostMapping("/{topic_id}/items/{rated_item_id}/reviews")
    Result createRatedItemReview(@PathVariable Integer topic_id,
                                 @PathVariable Integer rated_item_id,
                                 @RequestBody CreateRatedItemReviewBody createRatedItemReviewBody) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        createRatedItemReviewBody.setRated_item_id(rated_item_id);
        Integer addReview =
                ratedItemReviewService.addReview(createRatedItemReviewBody);
        if (addReview < 0) return Result.error("添加失败!");
        else return Result.success();
    }

    @PutMapping("/{topic_id}/items/{rated_item_id}/reviews/{review_id}")
    Result deleteRatedItemReview(@PathVariable Integer topic_id,
                                 @PathVariable Integer rated_item_id,
                                 @PathVariable Integer review_id) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer deleteReview = ratedItemReviewService.deleteReview(review_id);
        if (deleteReview < 0) return Result.error("删除失败!");
        else return Result.success();
    }

    @PostMapping("/{topic_id}/items/{rated_item_id}/reviews/{review_id}/likes")
    Result addLikeRatedItemReview(@PathVariable Integer topic_id,
                                  @PathVariable Integer rated_item_id,
                                  @PathVariable Integer review_id) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer liked = ratedItemReviewService.likeTheReview(review_id);
        if (liked < 0) return Result.error("点赞失败!");
        else return Result.success();
    }

    @DeleteMapping("/{topic_id}/items/{rated_item_id}/reviews/{review_id}/likes")
    Result deleteLikeRatedItemReview(@PathVariable Integer topic_id,
                                     @PathVariable Integer rated_item_id,
                                     @PathVariable Integer review_id) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer liked = ratedItemReviewService.unlikeTheReview(review_id);
        if (liked < 0) return Result.error("取消点赞失败!");
        else return Result.success();
    }

    private Integer getUidByToken() {
        return TokenMessage.getUidByToken(request);
    }
}
