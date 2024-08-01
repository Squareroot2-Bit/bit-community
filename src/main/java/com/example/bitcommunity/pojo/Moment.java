package com.example.bitcommunity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Moment {
    private int mid;    //moment id
    private int uid;    //user's uid
    private String imgs;
    private String title;   //标题
    private int type;       //类型（暂时用不到，默认设0）
    private String content; //内容
    private String cover_image_url; //内容图片url
    private int likes;      //点赞数
    private int is_deleted; //内容是否删除：0 -> 存在, 1 -> 删除
    private LocalDateTime create_time;      //创建时间
    private LocalDateTime update_time;
    private int top_mrid;   //置顶的 moment review 的id，如无置顶设为-1
}
