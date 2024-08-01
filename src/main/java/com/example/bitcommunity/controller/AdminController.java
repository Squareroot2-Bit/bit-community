package com.example.bitcommunity.controller;

import com.example.bitcommunity.json.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AdminController
 * @Description
 * @date 2023/12/28 21:04
 * @Author Squareroot_2
 */
@RestController
@RequestMapping("/admin/manager")
public class AdminController {
    @GetMapping("/")
    Result manager(){
        return Result.success();
    }
}
