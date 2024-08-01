package com.example.bitcommunity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentReview {
    private Integer mrid;   //moment review id
    private Integer mid;    //所属的moment的id
    private Integer uid;    //发布者uid
    private String review;  //回复内容
    private Integer to_mrid;     //回复的目标（二级评论），非二级评论默认为-1
    private Integer likes;  //点赞数
    private Integer is_deleted; //内容是否删除：0 -> 存在, 1 -> 删除
    private LocalDateTime create_time;
    private Integer to_uid;
    private Integer type;
    private Integer direct_mrid;
}
