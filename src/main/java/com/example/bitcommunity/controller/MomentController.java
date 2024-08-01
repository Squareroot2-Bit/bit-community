package com.example.bitcommunity.controller;

import com.example.bitcommunity.json.Result;
import com.example.bitcommunity.json.body.CreateMomentBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentData;
import com.example.bitcommunity.service.MomentService;
import com.example.bitcommunity.service.TokenMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moments")
public class MomentController {
    @Autowired
    MomentService momentService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping
    Result browseMoment(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List<MomentBriefData> momentBriefDataList = momentService.getMomentList(new PageBody(offset, limit));
        Map<String, Object> res = new HashMap<>();
        res.put("moments", momentBriefDataList);
        res.put("num", momentBriefDataList.size());
        return Result.success(res);
    }

    @GetMapping("/{momentid}")
    Result viewMoment(@PathVariable("momentid") int mid) {
        MomentData momentData = momentService.getMoment(mid);
        return Result.success(momentData);
    }

    @PostMapping
    Result createMoment(@RequestBody CreateMomentBody createMomentBody) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        Integer mid = momentService.addMoment(createMomentBody);
        Map<String, Integer> res = new HashMap<>();
        res.put("mid", mid);
        return mid != -1 ? Result.success(res) :
                Result.error("创建失败！");
    }

    @DeleteMapping("/{momentid}")
    Result deleteMoment(@PathVariable("momentid") int mid) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        return momentService.deleteMoment(mid) ? Result.success() : Result.error("删除moment失败");
    }

    @DeleteMapping("/{momentid}/likes")
    Result deleteMomentLike(@PathVariable("momentid") int mid) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        return momentService.deleteMomentLike(mid) ? Result.success() : Result.error("取消moment点赞失败");
    }

    @PostMapping("/{momentid}/likes")
    Result addMomentLike(@PathVariable("momentid") int mid) {
        int uid = getUidByToken();
        switch (uid) {
            case -1 -> {
                return Result.error("创建失败！");
            }
            case -2 -> {
                return Result.notLogin();
            }
            case -3 -> {
                return Result.tokenExpired();
            }
        }
        return momentService.addMomentLike(mid) ? Result.success() : Result.error("添加moment点赞失败");
    }

    private Integer getUidByToken() {
        return TokenMessage.getUidByToken(request);
    }
}
