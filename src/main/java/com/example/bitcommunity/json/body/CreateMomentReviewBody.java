package com.example.bitcommunity.json.body;

import lombok.Data;

/**
 * @ClassName CreateMomentReviewBody
 * @Description
 * @date 2023/11/27 2:09
 * @Author Squareroot_2
 */
@Data
public class CreateMomentReviewBody {
    private String content;
    private Integer to_momentrid; //如果没有该属性，则表示是一级评论
}