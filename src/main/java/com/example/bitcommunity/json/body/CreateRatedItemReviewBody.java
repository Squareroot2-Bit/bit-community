package com.example.bitcommunity.json.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CreateRatedItemReviewBody
 * @Description
 * @date 2023/12/23 6:17
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRatedItemReviewBody {
    private int rated_item_id;  //所属的评分项的id
    private String content;      //评论内容
    private int to_review_id;   //"若为二级评论，则为父评论review_id",否则为0
}
