package com.example.bitcommunity.json.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CreateMomentBody
 * @Description
 * @date 2023/11/27 2:09
 * @Author Squareroot_2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMomentBody {
    private Boolean anonymous;
    private String content;
    private Integer type;
    private List<String> media;
    private String cover_image;
    private String title;
}
