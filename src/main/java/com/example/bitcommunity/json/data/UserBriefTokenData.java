package com.example.bitcommunity.json.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBriefTokenData {
    private String avatar;
    private String nickname;
    private Integer uid;
    private String token;
}
