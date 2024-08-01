package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.pojo.UserInfo;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName TopicWithPublisher
 * @Description
 * @date 2023/12/19 0:06
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicWithPublisher {
    private Topic topic;        //主题topic信息
    private UserBrief publisher;//发布者信息
    private List<RatedItemWithPublisher> ratedItems;

    public TopicWithPublisher(Topic topic, UserInfo publisher, List<RatedItemWithPublisher> ratedItems) {
        this(topic, new UserBrief(publisher), ratedItems);
    }

    /*public TopicWithPublisher(Topic topic, UserInfo publisher) {
        this(topic.getTopic_id(),
                topic.getName(),
                topic.getBackground(),
                topic.getDescription(),
                topic.getUid(),
                topic.getCreate_time(),
                topic.getIs_deleted(),
                topic.getValid(),
                topic.getChecked(),
                publisher.getNickname(),
                publisher.getAvatar_url(),
                publisher.getGender());
    }*/
}
