package com.example.bitcommunity.service.score;

import com.example.bitcommunity.json.body.CreateTopicBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.pojo.score.TopicWithPublisher;

import java.util.List;

/**
 * @ClassName TopicService
 * @Description
 * @date 2023/12/20 10:51
 * @Author Squareroot_2
 */
public interface TopicService {
    /**
     * 获得包括三个评分人数最多ratedItem主要信息的topic信息列表
    * */
    List<TopicWithPublisher> getTopicList(PageBody pageBody);

    /**
     * 根据topic_id，获得不包括评分项的topic信息
     * */
    TopicWithPublisher getTopicByIdFromAll(int topic_id);

    TopicWithPublisher getTopicByIdFromValid(int topic_id);

    List<TopicWithPublisher> selectTopic(String keyword, PageBody pageBody);

    Integer addTopic(CreateTopicBody createTopicBody);

    Integer deleteTopic(int topic_id);
}
