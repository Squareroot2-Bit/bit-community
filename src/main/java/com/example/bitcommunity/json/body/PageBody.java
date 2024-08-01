package com.example.bitcommunity.json.body;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBody {
    private Integer offset;
    private Integer limit;
}
