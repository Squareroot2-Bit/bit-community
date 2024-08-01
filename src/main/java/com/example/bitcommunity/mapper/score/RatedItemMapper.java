package com.example.bitcommunity.mapper.score;

import com.example.bitcommunity.pojo.score.RatedItem;
import com.example.bitcommunity.pojo.score.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName RatedItemMapper
 * @Description
 * @date 2023/12/19 2:24
 * @Author Squareroot_2
 */
@Mapper
public interface RatedItemMapper {
    @Select("""
            select *
            from bit_community.rated_item
            where is_deleted = 0
            order by people_sum desc
            limit #{offset}, #{limit};
            """)
        //用户浏览时调用的select
    List<RatedItem> selectForBrowse(Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.rated_item
            where is_deleted = 0 and name like #{keyword}
            order by create_time desc
            limit #{offset}, #{limit};""")
        //用户搜索时调用的select
    List<RatedItem> selectForSearch(String keyword, Integer offset, Integer limit);

    /*@Select("""
            select *
            from bit_community.rated_item
            where is_deleted = 0 and topic_id = #{topic_id};""")
        //根据topic_id，从未删除的rated_item中查找
    List<RatedItem> selectByTopic(Integer topic_id);*/

    @Select("""
            select *
            from bit_community.rated_item
            where is_deleted = 0 and topic_id = #{topic_id}
            order by people_sum desc
            limit #{offset}, #{limit};""")
        //根据topic_id，依据评分人数排序
    List<RatedItem> selectByTopic(Integer topic_id, Integer offset, Integer limit);

    @Select("""
            select *
            from bit_community.rated_item
            where rated_item_id = #{rated_item_id};""")
        //根据id，从所有trated_item中查找
    RatedItem selectByIdFromAll(Integer rated_item_id);

    @Select("""
            select *
            from bit_community.rated_item
            where is_deleted = 0 and rated_item_id = #{rated_item_id};""")
        //根据id，从未删除（is_deleted = 0）的rated_item中查找
    RatedItem selectByIdFromUndeleted(Integer rated_item_id);

    @Options(keyProperty = "rated_item_id", useGeneratedKeys = true)
    @Insert("""
            insert into bit_community.rated_item(
                topic_id, name, image, description, uid,
                people_10, people_8, people_6, people_4, people_2,
                people_sum, create_time, update_time, is_deleted)
            values (
                #{topic_id}, #{name}, #{image}, #{description}, #{uid},
                0, 0, 0, 0, 0, 0, NOW(), NOW(), 0);
            """)
    //创建rated_item
    int insert(RatedItem ratedItem);

    @Update("""
            update bit_community.rated_item
            set topic_id = #{topic_id}, name = #{name}, image = #{image},
                description = #{description}, uid = #{uid},
                people_10 = #{people_10}, people_8 = #{people_8},
                people_6 = #{people_6}, people_4 = #{people_4},
                people_2 = #{people_2}, people_sum = #{people_sum},
                create_time = #{create_time}, update_time = #{update_time},
                is_deleted = #{is_deleted}
            where rated_item_id = #{rated_item_id};
            """)
    //修改rated_item
    int update(RatedItem ratedItem);

    @Update("""
            update bit_community.rated_item
            set people_10 = people_10 + 1, people_sum = people_sum + 1
            where rated_item_id = #{rated_item_id};
            """)
        //增加打10分的人数
    int addScore10(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_10 = people_10 - 1, people_sum = people_sum - 1
            where rated_item_id = #{rated_item_id};
            """)
        //减少打10分的人数
    int deleteScore10(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_8 = people_8 + 1, people_sum = people_sum + 1
            where rated_item_id = #{rated_item_id};
            """)
        //增加打8分的人数
    int addScore8(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_8 = people_8 - 1, people_sum = people_sum - 1
            where rated_item_id = #{rated_item_id};
            """)
        //减少打8分的人数
    int deleteScore8(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_6 = people_6 + 1, people_sum = people_sum + 1
            where rated_item_id = #{rated_item_id};
            """)
        //增加打6分的人数
    int addScore6(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_6 = people_6 - 1, people_sum = people_sum - 1
            where rated_item_id = #{rated_item_id};
            """)
        //减少打6分的人数
    int deleteScore6(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_4 = people_4 + 1, people_sum = people_sum + 1
            where rated_item_id = #{rated_item_id};
            """)
        //增加打4分的人数
    int addScore4(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_4 = people_4 - 1, people_sum = people_sum - 1
            where rated_item_id = #{rated_item_id};
            """)
        //减少打4分的人数
    int deleteScore4(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_2 = people_2 + 1, people_sum = people_sum + 1
            where rated_item_id = #{rated_item_id};
            """)
        //增加打2分的人数
    int addScore2(Integer rated_item_id);

    @Update("""
            update bit_community.rated_item
            set people_2 = people_2 - 1, people_sum = people_sum - 1
            where rated_item_id = #{rated_item_id};
            """)
        //减少打2分的人数
    int deleteScore2(Integer rated_item_id);



    @Update("""
            update bit_community.rated_item
            set is_deleted = 1, update_time = NOW()
            where rated_item_id = #{rated_item_id};
            """)
    //删除rated_item，删除权限在service层中判断
    int delete(Integer rated_item_id);

}
