package com.example.bitcommunity.json.data;

import com.example.bitcommunity.pojo.score.RatedItemReviewWithBrowser;
import com.example.bitcommunity.pojo.score.RatedItemWithBrowser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ScoreData
 * @Description
 * @date 2023/12/30 20:35
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreData {
    List<Integer> people_nums;
    Double average_score;

    private ScoreData(Integer p2, Integer p4, Integer p6, Integer p8, Integer p10, Double average_score) {
        people_nums = List.of(new Integer[]{p2, p4, p6, p8, p10});
        this.average_score = average_score;
    }

    public ScoreData(RatedItemWithBrowser ratedItem){
        this(ratedItem.getRatedItemWithPublisher().getRatedItem().getPeople_2(),
                ratedItem.getRatedItemWithPublisher().getRatedItem().getPeople_4(),
                ratedItem.getRatedItemWithPublisher().getRatedItem().getPeople_6(),
                ratedItem.getRatedItemWithPublisher().getRatedItem().getPeople_8(),
                ratedItem.getRatedItemWithPublisher().getRatedItem().getPeople_10(),
                ratedItem.getRatedItemWithPublisher().getRatedItem().getAvg_score());
    }
}
