package com.example.bitcommunity.mapper.score;

import com.example.bitcommunity.pojo.score.RatedItemReviewLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName RatedItemReviewLikeMapper
 * @Description
 * @date 2023/12/19 22:20
 * @Author Squareroot_2
 */
@Mapper
public interface RatedItemReviewLikeMapper {

    @Select("""
            select *
            from bit_community.rated_review_like
            where review_id = #{rated_item_review_id} and uid = #{uid};
            """)
    RatedItemReviewLike select(Integer rated_item_review_id, Integer uid);

    @Select("""
            select COUNT(*)
            from bit_community.rated_review_like
            where review_id = #{rated_item_review_id};
            """)
    int count(Integer rated_item_review_id);

    @Insert("""
            insert into bit_community.rated_review_like (review_id, uid)
            values (#{review_id}, #{uid});
            """)
    int insert(RatedItemReviewLike ratedItemReviewLike);

    @Delete("""
            delete from bit_community.rated_review_like
            where review_id = #{review_id} and uid = #{uid};
            """)
    int delete(RatedItemReviewLike ratedItemReviewLike);
}
