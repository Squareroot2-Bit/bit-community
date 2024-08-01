package com.example.bitcommunity.pojo.score;

import com.example.bitcommunity.json.body.CreateRatedItemReviewBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName RatedItemReview
 * @Description
 * @date 2023/12/19 21:08
 * @Author Squareroot_2
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatedItemReview {
    private int review_id;      //评分项回复的主键id
    private int rated_item_id;  //所属的评分项的id
    private int uid;            //发送评论者uid
    private String review;      //评论内容
    private int to_review_id;   //"若为二级评论，则为父评论review_id",否则为-1
    private int to_uid;         //"若为二级评论，则为父评论uid",否则为-1
    private int direct_review_id;   //"若为二级评论，为直属顶层评论的review_id",否则为-1
    private int likes;          //点赞数量
    private LocalDateTime create_time;    //创建时间
    private int is_deleted;     //是否已删除
    private int type;

    public RatedItemReview(CreateRatedItemReviewBody createRatedItemReviewBody, int uid, int to_uid,int direct_review_id) {
        this(0,
                createRatedItemReviewBody.getRated_item_id(),
                uid,
                createRatedItemReviewBody.getContent(),
                createRatedItemReviewBody.getTo_review_id(),
                to_uid,
                direct_review_id,
                0, null, 0, 0);
    }
}
