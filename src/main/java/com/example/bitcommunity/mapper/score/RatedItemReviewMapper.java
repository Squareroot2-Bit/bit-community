package com.example.bitcommunity.mapper.score;

import com.example.bitcommunity.pojo.score.RatedItemReview;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName RatedItemReviewMapper
 * @Description
 * @date 2023/12/19 21:13
 * @Author Squareroot_2
 */
@Mapper
public interface RatedItemReviewMapper {
    @Select("""
            select *
            from bit_community.rated_item_review
            where is_deleted = 0 and rated_item_id = #{rated_item_id}
                and to_review_id = -1
            order by create_time desc
            limit #{offset}, #{limit};
            """)
        //根据rated_item_id，从未删除的rated_item_review中查找
        //并按照时间从新到旧展示
    List<RatedItemReview> selectByRatedItem(Integer rated_item_id, Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.rated_item_review
            where is_deleted = 0 and rated_item_id = #{rated_item_id}
                and to_review_id = #{to_review_id}
            order by create_time desc;
            """)
        //根据to_review_id，从未删除的rated_item_review中查找
        //并按照时间从新到旧展示
    List<RatedItemReview> selectByToReview(Integer to_review_id);


    @Select("""
            select *
            from bit_community.rated_item_review
            where is_deleted = 0 and rated_item_id = #{rated_item_id}
            order by likes desc
            limit 1;
            """)
        //根据rated_item_id，查找点赞数最多的
    List<RatedItemReview> selectTheBestOfRatedItem(Integer rated_item_id);


    @Select("""
            select *
            from bit_community.rated_item_review
            where review_id = #{review_id};""")
        //根据review_id，从所有的rated_item_review中查找
    RatedItemReview selectByIdFromAll(Integer review_id);

    @Select("""
            select *
            from bit_community.rated_item_review
            where is_deleted = 0 and review_id = #{review_id};""")
        //根据review_id，从未删除（is_deleted = 0）的rated_item_review中查找
    RatedItemReview selectByIdFromUndeleted(Integer review_id);

    @Options(keyProperty = "review_id", useGeneratedKeys = true)
    @Insert("""
            insert into bit_community.rated_item_review (
                rated_item_id, uid, review,
                to_review_id, to_uid, direct_review_id,
                likes, create_time, is_deleted, type)
            values (#{rated_item_id}, #{uid}, #{review},
                #{to_review_id}, #{to_uid}, #{direct_review_id},
                #{likes}, #{create_time}, #{is_deleted}, #{type});
            """)
        //创建rated_item_review
    int insert(RatedItemReview review);

    @Update("""
            update bit_community.rated_item_review
            set rated_item_id = #{rated_item_id}, uid = #{uid},
                review = #{review}, to_review_id = #{to_review_id},
                to_uid = #{to_uid}, direct_review_id = #{direct_review_id},
                likes = #{likes}, create_time = #{create_time},
                is_deleted = #{is_deleted}, type  = #{type}
            where review_id = #{review_id};
            """)
    int update(RatedItemReview review);

    @Update("""
            update bit_community.rated_item_review
            set is_deleted = 1
            where review_id = #{review_id};
            """)
    int delete(Integer review_id);

    @Update("""
            update bit_community.rated_item_review
            set likes = likes + 1
            where review_id = #{review_id};
            """)
    int addLike(Integer review_id);
    @Update("""
            update bit_community.rated_item_review
            set likes = likes - 1
            where review_id = #{review_id};
            """)
    int deleteLike(Integer review_id);

    @Update("""
            update bit_community.rated_item_review
            set likes = (
                select COUNT(uid)
                from bit_community.rated_review_like
                where rated_review_like.review_id = #{review_id}
            )
            where review_id = #{review_id};
            """)
        //防止点赞数据不一致，刷新点赞数据
    int refreshLikes(Integer review_id);
}
