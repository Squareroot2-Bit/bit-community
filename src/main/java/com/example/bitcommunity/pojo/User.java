package com.example.bitcommunity.pojo;

import com.example.bitcommunity.json.body.LoginAndRegisterBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;        //user id
    private String bitid;      //bit id 学号，这里最好该为字符串
    private String email;   //暂时没用
    private String password;//密码的加盐哈希值
    private String salt;    //盐：长度为20的随机字符串
    private LocalDateTime create_time;      //创建时间
    private LocalDateTime update_time;      //修改时间
    private LocalDateTime last_login_time;  //最后登录时间
    private int status;     //用户状态：0 -> 不在线, 1 -> 在线
    private int is_deleted; //用户是否注销：0 -> 活跃, 1 -> 注销
}
