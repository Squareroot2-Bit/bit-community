package com.example.bitcommunity.mapper.score;

import com.example.bitcommunity.pojo.score.ScoreInfo;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName ScoreInfoMapper
 * @Description
 * @date 2023/12/19 23:17
 * @Author Squareroot_2
 */
@Mapper
public interface ScoreInfoMapper {
    @Select("""
            select *
            from bit_community.score_info
            where rated_item_id = #{rated_item_id} and uid = #{uid};
            """)
    ScoreInfo selectScore(Integer rated_item_id, Integer uid);

    @Insert("""
            insert into bit_community.score_info (rated_item_id, uid, score)
            values (#{rated_item_id}, #{uid}, #{score});
            """)
    int insert(ScoreInfo scoreInfo);
    @Update("""
            update bit_community.score_info
            set score = #{score}
            where rated_item_id = #{rated_item_id} and uid = #{uid};
            """)
    int update(ScoreInfo scoreInfo);
    @Delete("""
            delete from bit_community.score_info
            where rated_item_id = #{rated_item_id} and uid = #{uid};
            """)
    int delete(Integer rated_item_id, Integer uid);
}
