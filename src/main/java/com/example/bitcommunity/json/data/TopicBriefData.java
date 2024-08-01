package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.TopicWithPublisher;
import com.example.bitcommunity.pojo.user.UserBrief;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TopicBriefData
 * @Description
 * @date 2023/12/30 17:16
 * @Author Squareroot_2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicBriefData {
    private long topicId;
    private String name;
    private String background;
    private String description;
    private UserBriefData user;

    public TopicBriefData(TopicWithPublisher topic) {
        this.topicId = topic.getTopic().getTopic_id();
        this.name = topic.getTopic().getName();
        this.background = topic.getTopic().getBackground();
        this.description = topic.getTopic().getDescription();
        this.user = new UserBriefData(topic.getPublisher());
    }
}
