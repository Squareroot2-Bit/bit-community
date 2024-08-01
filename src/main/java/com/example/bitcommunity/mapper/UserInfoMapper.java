package com.example.bitcommunity.mapper;

import com.example.bitcommunity.pojo.User;
import com.example.bitcommunity.pojo.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoMapper {
    @Insert("insert into userinfo(uid, nickname, avatar_url, create_time, update_time) " +
            "values(#{uid}, #{nickname}, #{avatar_url}, #{create_time}, #{update_time})")
    int insert(UserInfo userinfo);

    @Delete("delete from userinfo where uid = #{uid}")
    int delete(Integer uid);

    @Update("update userinfo set nickname=#{nickname}, avatar_url=#{avatar_url}, update_time=#{updata_time} " + "" +
            "where uid=#{uid}")
    int update(UserInfo userInfo);

    @Select("select * from userinfo where uid=#{uid}")
    UserInfo selectByUid(Integer uid);
}
