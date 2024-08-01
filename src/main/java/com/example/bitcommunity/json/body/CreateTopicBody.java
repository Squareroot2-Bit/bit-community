package com.example.bitcommunity.json.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CreateTopicBody
 * @Description
 * @date 2023/12/20 12:21
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTopicBody {
    private String name;    //主题的名称
    private String background;  //主题的背景
    private String description; //主题的简介
    private List<CreateRatedItemBody> items;
}
