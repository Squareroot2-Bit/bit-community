package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.json.body.CreateTopicBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * @ClassName Topic
 * @Description
 * @date 2023/12/17 16:53
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    private int topic_id;   //主键
    private String name;    //主题的名称
    private String background;  //主题的背景
    private String description; //主题的简介
    private int uid;    //主题的作者的uid
    private LocalDateTime create_time;
    private int is_deleted;
    private int valid;      //有效位
    private int checked;    //审核位

    //TODO 用于新建Topic
    public Topic(@NonNull CreateTopicBody createTopicBody, int uid) {
        this(0,
                createTopicBody.getName(),
                createTopicBody.getBackground(),
                createTopicBody.getDescription(),
                uid,
                null,
                0,0,0);
    }
}
