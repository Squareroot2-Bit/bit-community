package com.example.bitcommunity.service.score.impl;

import com.example.bitcommunity.json.body.CreateTopicBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.mapper.UserInfoMapper;
import com.example.bitcommunity.mapper.score.RatedItemMapper;
import com.example.bitcommunity.mapper.score.RatedItemReviewMapper;
import com.example.bitcommunity.mapper.score.TopicMapper;
import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.score.*;
import com.example.bitcommunity.service.TokenMessage;
import com.example.bitcommunity.service.score.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TopicServiceImpl
 * @Description
 * @date 2023/12/21 18:28
 * @Author Squareroot_2
 */
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {
    private static final int DefaultRatedItemLimit = 3;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RatedItemMapper ratedItemMapper;
    @Autowired
    private RatedItemReviewMapper ratedItemReviewMapper;
    @Autowired
    private HttpServletRequest request;


    /**
     * @param pageBody
     * @return 获得包括三个评分人数最多ratedItem主要信息的topic信息列表
     */
    @Override
    public List<TopicWithPublisher> getTopicList(PageBody pageBody) {
        List<Topic> topicList = topicMapper.selectForBrowse(
                pageBody.getOffset(),
                pageBody.getLimit());
        return getTopicWithPublishers(topicList);
    }

    /**
     * @param topic_id
     * @return 根据topic_id，从所有的topic中获得不包括评分项的topic信息
     */
    @Override
    public TopicWithPublisher getTopicByIdFromAll(int topic_id) {
        Topic topic = topicMapper.selectByIdFromAll(topic_id);
        if (topic == null) return null;
        UserInfo publisher = userInfoMapper.selectByUid(topic.getUid());
        return new TopicWithPublisher(topic, publisher, null);
    }

    /**
     * @param topic_id
     * @return 根据topic_id，从过审的topic中获得不包括评分项的topic信息
     */
    @Override
    public TopicWithPublisher getTopicByIdFromValid(int topic_id) {
        Topic topic = topicMapper.selectByIdFromValid(topic_id);
        if (topic == null) return null;
        UserInfo publisher = userInfoMapper.selectByUid(topic.getUid());
        return new TopicWithPublisher(topic, publisher, null);
    }

    /**
     * @param keyword
     * @param pageBody
     * @return 返回搜索关键词得到的Topic分页结果
     */
    @Override
    public List<TopicWithPublisher> selectTopic(String keyword, PageBody pageBody) {
        List<Topic> topicList = topicMapper.selectForSearch(
                keyword, pageBody.getOffset(), pageBody.getLimit());
        return getTopicWithPublishers(topicList);
    }

    /**
     * @param createTopicBody
     * @return 向数据库内添加新的topic
     * >0: 成功添加
     * -1: 用户未登录
     */
    @Override
    public Integer addTopic(CreateTopicBody createTopicBody) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        Topic topic = new Topic(createTopicBody, uid);
        int insert = topicMapper.insert(topic);
        if (insert < 0) return insert;
        else return topic.getTopic_id();
    }

    /**
     * @param topic_id
     * @return >=0: 正确删除
     * -1: 用户未登录
     * -2: 该topic不存在或已经被删除
     * -3: 该用户无权限删除该topic
     */
    @Override
    public Integer deleteTopic(int topic_id) {
        Integer uid = getUidByToken();
        if (uid < 0) return -1;
        Topic topic = topicMapper.selectByIdFromAll(topic_id);
        if (topic == null || topic.getIs_deleted() == 1)
            return -2;
        int publisherUid = topic.getUid();
        if (uid != publisherUid) return -3;
        return topicMapper.delete(topic_id);
    }

    /**
     * @param topicList
     * @return 根据topicList生成对应的包括三个评分人数最多ratedItem主要信息的List<TopicWithPublisher>
     */
    private List<TopicWithPublisher> getTopicWithPublishers(List<Topic> topicList) {
        return topicList.stream().map(topic -> {
            UserInfo publisher = userInfoMapper.selectByUid(topic.getUid());
            List<RatedItemWithPublisher> ratedItemWithPublisherList =
                    ratedItemMapper.selectByTopic(topic.getTopic_id(),
                                    0,
                                    DefaultRatedItemLimit)
                            .stream()
                            .map(ratedItem -> {
                                UserInfo ratedItemPublisher =
                                        userInfoMapper.selectByUid(
                                                ratedItem.getUid());
                                List<RatedItemReview> reviews = ratedItemReviewMapper
                                        .selectTheBestOfRatedItem(
                                                ratedItem.getRated_item_id());
                                RatedItemReview bestReview = reviews.isEmpty() ? null : reviews.get(0);
                                return new RatedItemWithPublisher(
                                        ratedItem,
                                        ratedItemPublisher,
                                        bestReview);
                            })
                            .toList();
            return new TopicWithPublisher(topic, publisher, ratedItemWithPublisherList);
        }).toList();
    }

    private Integer getUidByToken() {
        return TokenMessage.getUidByToken(request);
    }
}
