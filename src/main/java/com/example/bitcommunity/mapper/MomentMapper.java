package com.example.bitcommunity.mapper;

import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentData;
import com.example.bitcommunity.pojo.Moment;
import com.example.bitcommunity.pojo.MomentAndUserInfo;
import com.example.bitcommunity.pojo.sql.MomentForBrowse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MomentMapper {
    // uid 用来判断用户是否点赞 moment
    List<MomentForBrowse> selectForBrowse(Integer offset, Integer limit, Integer uid);
    List<MomentForBrowse> selectForBrowseNotLogin(Integer offset, Integer limit);

    // uid 用来判断用户是否点赞 moment
    MomentForBrowse selectByMid(Integer mid, Integer uid);
    MomentForBrowse selectByMidNotLogin(Integer mid);

    @Select("""
            select COUNT(mrid)
            from bit_community.moment_review
            where mid = #{mid};
            """)
    int selectReviewerNum(Integer mid);

    @Options(keyProperty = "mid", useGeneratedKeys = true)
    @Insert("insert into moment(uid, title, content, type, cover_image_url, create_time, update_time, likes, is_deleted, imgs) " +
            "values(#{uid}, #{title}, #{content}, #{type}, #{cover_image_url}, #{create_time}, #{update_time}, #{likes}, #{is_deleted}, #{imgs})")
    int insert(Moment moment);

    @Update("update moment set is_deleted=1 where mid=#{mid} and uid=#{uid}")
    int delete(Integer mid, Integer uid);

    @Update("update moment set likes=likes+1 where mid=#{mid}")
    int addLike(Integer mid);

    @Update("update moment set likes=likes-1 where mid=#{mid}")
    int deleteLike(Integer mid);
}
