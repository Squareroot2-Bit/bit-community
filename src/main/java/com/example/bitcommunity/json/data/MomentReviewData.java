package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.sql.MomentReviewForBrowse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MomentReviewData {
    private String content;
    private LocalDateTime create_time;
    private Boolean is_liked;    //是否点赞
    private Integer likes;
    private Integer momentrid;
    private UserBriefData reviewer;
    private List<SubMomentReviewData> sub_reviews;
    private Integer total_sub_reviews;

    public MomentReviewData(MomentReviewForBrowse m) {
        this(m.getReview(), m.getCreate_time(), m.getIs_liked(), m.getLikes(), m.getMrid(),
                new UserBriefData(m.getAvatar_url(), m.getNickname(), m.getUid()), null, 0);
    }

    public void addSubReview(MomentReviewForBrowse m) {
        if(sub_reviews == null) {
            sub_reviews = new ArrayList<>();
        }
        SubMomentReviewData sub= new SubMomentReviewData();
        sub.setContent(m.getReview());
        sub.setLikes(m.getLikes());
        sub.setIs_liked(m.getIs_liked());
        sub.setMomentrid(m.getMrid());
        sub.setReviewer(new UserBriefData(m.getAvatar_url(), m.getNickname(), m.getUid()));
        sub.setTarget(new TargetData(m.getTo_mrid(), new UserBriefData(m.getTo_avatar_url(), m.getTo_nickname(), m.getTo_uid())));
        sub_reviews.add(sub);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SubMomentReviewData {
    private String content;
    private String create_time;
    private Boolean is_liked;    //是否点赞
    private Integer likes;
    private Integer momentrid;
    private UserBriefData reviewer;
    private TargetData target;      //回复给谁的评论
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class TargetData {
    private Integer momentrid;
    private UserBriefData reviewer;
}