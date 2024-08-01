package com.example.bitcommunity.mapper.score;

import com.example.bitcommunity.pojo.score.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName TopicMapper
 * @Description
 * @date 2023/12/17 15:51
 * @Author Squareroot_2
 */
@Mapper
public interface TopicMapper {
    @Select("""
            select *
            from bit_community.topic
            where valid = 1 and is_deleted = 0
            order by create_time desc
            limit #{offset}, #{limit};""")
    //用户浏览时调用的select
    List<Topic> selectForBrowse(Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.topic
            where valid = 1 and is_deleted = 0 and name like #{keyword}
            order by create_time desc
            limit #{offset}, #{limit};""")
    //用户搜索时调用的select
    List<Topic> selectForSearch(String keyword, Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.topic
            where checked = 0 and is_deleted = 0
            order by create_time desc
            limit #{offset}, #{limit};""")
    //管理员审核时调用的select
    List<Topic> selectForCheck(Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.topic
            where topic_id = #{topic_id};""")
    //根据id，从所有topic中查找
    Topic selectByIdFromAll(Integer topic_id);

    @Select("""
            select *
            from bit_community.topic
            where is_deleted = 0 and valid = 1 and
                topic_id = #{topic_id};""")
    //根据id，从未删除且审核通过（is_deleted = 0 and valid = 1）的topic中查找
    Topic selectByIdFromValid(Integer topic_id);

    @Options(keyProperty = "topic_id", keyColumn = "topic_id", useGeneratedKeys = true)
    @Insert("""
            insert into bit_community.topic(name, background, description, uid,
                create_time, is_deleted, valid, checked)
            values(#{name}, #{background}, #{description}, #{uid},
            NOW(), 0, 1, 1);
            """)
    //创建topic
    int insert(Topic topic);
    @Update("""
            update bit_community.topic
            set name = #{name}, background = #{background},
                description = #{description}, uid = #{uid},
                create_time = #{create_time}, is_deleted = #{is_deleted},
                valid = #{valid}, checked = #{checked}
            where topic_id = #{topic_id};
            """)
    //修改topic
    int update(Topic topic);

    @Update("""
            update bit_community.topic
            set is_deleted = 1
            where topic_id = #{topic_id};
            """)
    //删除topic，删除权限在service层中判断
    int delete(Integer topic_id);

    @Update("""
            update bit_community.topic
            set valid = #{valid}, checked = 1
            where topic_id = #{topic_id};
            """)
    //管理员审核topic
    int check(Integer topic_id, Integer valid);


}
