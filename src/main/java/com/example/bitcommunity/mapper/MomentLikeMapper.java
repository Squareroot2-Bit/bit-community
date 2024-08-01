package com.example.bitcommunity.mapper;

import com.example.bitcommunity.pojo.MomentLike;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MomentLikeMapper {
    @Select("select count(*) from moment_likes where mid=#{mid}")
    int count(Integer mid);

    @Insert("insert into moment_likes(mid, uid) values (#{mid}, #{uid})")
    int insert(MomentLike momentLike);

    @Delete("delete from moment_likes where uid=#{uid} and mid=#{mid}")
    int delete(MomentLike momentLike);

    @Select("select * from moment_likes where uid=#{uid} and mid=#{mid}")
    MomentLike selectByMidAndUid(Integer mid, Integer uid);
}
