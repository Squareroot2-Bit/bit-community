package com.example.bitcommunity.mapper.administrator;

import com.example.bitcommunity.pojo.administrator.Administrator;
import org.apache.ibatis.annotations.*;

/**
 * @ClassName AdministratorMapper
 * @Description
 * @date 2023/12/19 23:38
 * @Author Squareroot_2
 */
@Mapper
public interface AdministratorMapper {

    @Select("""
            select *
            from bit_community.administrator
            where admin_id = #{admin_id};
            """)
    Administrator selectById(Integer admin_id);

    @Select("""
            select *
            from bit_community.administrator
            where name = #{name};
            """)
    Administrator selectByName(String name);

    @Options(keyProperty = "admin_id", useGeneratedKeys = true)
    @Insert("""
            insert into bit_community.administrator(
                name, password, create_time, update_time, is_deleted)
            values (#{name}, #{password}, #{create_time},
                #{update_time}, #{is_deleted});
            """)
    int insert(Administrator administrator);

    @Update("""
            update bit_community.administrator
            set name = #{name}, password = #{password},
                create_time = #{create_time}, update_time = #{update_time}, 
                is_deleted = #{is_deleted} 
            where admin_id = #{admin_id};
            """)
    int update(Administrator administrator);

    @Delete("""
            delete from bit_community.administrator
            where admin_id = #{admin_id};
            """)
    int delete(Integer admin_id);


}
