package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.TopicWithPublisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName TopicData
 * @Description
 * @date 2023/12/23 16:01
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicData {
    private long id;
    private String name;
    private String img;
    private String description;
    /**
     * 发布者的uid（这里不用再同时头像，昵称等信息），仅为了使管理员方便删除
     */
    private long uid;
    /**
     * 该主题下的打分人数(即所有评分项的评分人数之和)
     */
    private long peopleScored;  //该项目前还未实现,待完善
    /**
     * 该主题下评分人数前三多的评分项（不足三个的话有几个显示几个）
     */
    private List<RatedItemBriefData> max_item;

    public TopicData(TopicWithPublisher topic) {
        this.id = topic.getTopic().getTopic_id();
        this.name = topic.getTopic().getName();
        this.img = topic.getTopic().getBackground();
        this.description = topic.getTopic().getDescription();
        this.uid = topic.getTopic().getUid();
        this.peopleScored = 0;
        this.max_item = topic.getRatedItems().stream()
                .map(RatedItemBriefData::new)
                .toList();
    }

}
