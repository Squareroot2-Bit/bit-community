package com.example.bitcommunity.mapper;

import com.example.bitcommunity.pojo.User;
import com.example.bitcommunity.pojo.UserAndUserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Options(keyProperty = "uid", useGeneratedKeys = true) // 调用函数后user.uid为新注册用户的uid
    @Insert("insert into user(bitid, password, create_time, update_time) " +
            "values(#{bitid}, #{password}, #{create_time}, #{update_time})")
    int insert(User user);

    @Delete("delete from user where uid = #{uid}")
    int delete(Integer uid);

//    @Update("update user set bitid=#{bitid}, email=#{email}, nickname=#{nickname} where id=#{id}")
//    int update(User user);

    @Select("select * from user where bitid = #{bitid} and password = #{password}")
    User selectByBitidAndPassword(String bitid, String password);

    @Select("select * from user where bitid = #{bitid}")
    User selectByBitid(String bitid);

    @Select("""
        select user.uid, email, password, salt, user.create_time, user.update_time, user.last_login_time, status, is_deleted,
        avatar_url, nickname, gender, birthday, profile
        from user, userinfo where user.uid =userinfo.uid and user.uid=#{uid}
    """)
    UserAndUserInfo selectByUid(Integer uid);
}
