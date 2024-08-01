package com.example.bitcommunity.pojo.score;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ScoreInfo
 * @Description
 * @date 2023/12/19 17:56
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreInfo {
    private int rated_item_id;
    private int uid;
    private int score;
}
