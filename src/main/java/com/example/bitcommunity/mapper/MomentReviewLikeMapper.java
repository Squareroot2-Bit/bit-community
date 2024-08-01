package com.example.bitcommunity.mapper;

import com.example.bitcommunity.pojo.MomentLike;
import com.example.bitcommunity.pojo.MomentReview;
import com.example.bitcommunity.pojo.MomentReviewLike;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MomentReviewLikeMapper {
    @Select("select count(*) from moment_review_likes where mrid=#{mrid}")
    int count(Integer mrid);

    @Insert("insert into moment_review_likes(mrid, uid) values (#{mrid}, #{uid})")
    int insert(MomentReviewLike momentReviewLike);

    @Delete("delete from moment_review_likes where uid=#{uid} and mrid=#{mrid}")
    int delete(MomentReviewLike momentReviewLike);
}
