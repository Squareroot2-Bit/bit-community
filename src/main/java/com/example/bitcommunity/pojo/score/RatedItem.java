package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.json.body.CreateRatedItemBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * @ClassName RatedItem
 * @Description
 * @date 2023/12/18 22:59
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItem {
    private int rated_item_id;  //主键id
    private int topic_id;       //所属主题topic_id
    private String name;        //评分项名称
    private String image;       //评分项图片
    private String description; //评分项简介
    private int uid;            //评分项创建者uid
    private int people_10;
    private int people_8;
    private int people_6;
    private int people_4;
    private int people_2;
    private int people_sum;     //打分的总人数
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private int is_deleted;

    public double getAvg_score() {
        if (people_sum == 0) return 0.0;
        int score_sum = people_10 * 10 +
                        people_8 * 8 +
                        people_6 * 6 +
                        people_4 * 4 +
                        people_2 * 2;
        return (double) score_sum / (double) people_sum;
    }

    //TODO 用于新建RatedItem
    public RatedItem(@NonNull CreateRatedItemBody createRatedItemBody, int uid) {
        this(0,
                createRatedItemBody.getTopic_id(),
                createRatedItemBody.getName(),
                createRatedItemBody.getImage(),
                createRatedItemBody.getDescription(),
                uid,
                0, 0, 0, 0, 0, 0,
                null, null, 0);
    }
}
