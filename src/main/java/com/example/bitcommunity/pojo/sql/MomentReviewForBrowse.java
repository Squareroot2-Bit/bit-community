package com.example.bitcommunity.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MomentReviewForBrowse {
    private Integer mrid;
    private Integer mid;
    private String review;
    private Integer to_mrid;
    private Integer likes;
    private Boolean is_liked;
    private LocalDateTime create_time;
    private Integer uid;
    private String avatar_url;
    private String nickname;
    private Integer to_uid;
    private String to_avatar_url;
    private String to_nickname;
    private Integer type;
}
