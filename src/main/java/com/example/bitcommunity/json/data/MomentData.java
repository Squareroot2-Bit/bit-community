package com.example.bitcommunity.json.data;

//import com.bitcommunity.pojo.Moment;
import com.example.bitcommunity.pojo.sql.MomentForBrowse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class MomentData {
    private String title;
    private String content;
    private Integer type;
    private List<String> media;
    private long likes;
    private Boolean is_liked;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
    private UserBriefData user;
    private Integer total_review_num;
    public MomentData(MomentForBrowse m) {
        this(m.getTitle(), m.getContent(),m.getType(),
                Arrays.stream(m.getImgs().substring(1, m.getImgs().length()-1).split(", ")).toList(),
                m.getLikes(), m.getIs_liked(), m.getCreate_time(), m.getUpdate_time(),
                new UserBriefData(m.getAvatar_url(), m.getNickname(), m.getUid()),m.getTotal_review_num());
    }
}