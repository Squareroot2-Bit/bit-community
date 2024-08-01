package com.example.bitcommunity.mapper;

import com.example.bitcommunity.pojo.MomentReview;
import com.example.bitcommunity.pojo.sql.MomentForBrowse;
import com.example.bitcommunity.pojo.sql.MomentReviewForBrowse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MomentReviewMapper {
    // uid 用来判断用户是否点赞 moment
    List<MomentReviewForBrowse> selectDirectReview(Integer offset, Integer limit, Integer mid, Integer uid);
    List<MomentReviewForBrowse> selectDirectReviewNotLogin(Integer offset, Integer limit ,Integer mid);

    List<MomentReviewForBrowse> selectSubReview(Integer offset, Integer limit, Integer mid, Integer mrid, Integer uid);
    List<MomentReviewForBrowse> selectSubReviewNotLogin(Integer offset, Integer limit, Integer mrid, Integer mid);

    @Select("select * from moment_review where mrid = #{mrid}")
    MomentReview select(Integer mrid);

    @Options(keyProperty = "mrid", useGeneratedKeys = true)
    @Insert("insert into moment_review(mid, uid, review, to_mrid, likes, is_deleted, to_uid, create_time, type, direct_mrid) " +
            "values (#{mid}, #{uid}, #{review}, #{to_mrid}, #{likes}, #{is_deleted}, #{to_uid}, #{create_time}, #{type}, #{direct_mrid})")
    int insert(MomentReview momentReview);

    @Update("update moment_review set likes=likes+1 where mrid=#{mrid}")
    int addLike(Integer mrid);

    @Update("update moment_review set likes=likes-1 where mrid=#{mrid}")
    int deleteLike(Integer mrid);

    @Update("update moment_review set is_deleted=1 where mrid=#{mrid} and uid=#{uid}")
    int delete(Integer mrid, Integer uid);
}
