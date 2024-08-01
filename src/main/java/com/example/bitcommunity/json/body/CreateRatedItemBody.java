package com.example.bitcommunity.json.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CreateRatedItemBody
 * @Description
 * @date 2023/12/23 2:15
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRatedItemBody {
    private int topic_id;       //所属主题topic_id
    private String name;        //评分项名称
    private String image;       //评分项图片
    private String description; //评分项简介
}
