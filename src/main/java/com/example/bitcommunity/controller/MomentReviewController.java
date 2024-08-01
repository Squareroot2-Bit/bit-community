package com.example.bitcommunity.controller;

import com.example.bitcommunity.json.Result;
import com.example.bitcommunity.json.body.CreateMomentReviewBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentReviewData;
import com.example.bitcommunity.service.MomentReviewService;
import com.example.bitcommunity.service.TokenMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/moments")
public class MomentReviewController {
    @Autowired
    MomentReviewService momentReviewService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/{momentid}/reviews")
    Result browseMomentReview(@PathVariable("momentid") int mid, @RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        List<MomentReviewData> momentReviewDataList = momentReviewService.getMomentReviewList(new PageBody(offset, limit), mid);
        Map<String, Object> res = new HashMap<>();
        res.put("reviews", momentReviewDataList);
        res.put("num", momentReviewDataList.size());
        return Result.success(res);
    }

    @PostMapping("/{momentid}/reviews")
    Result createMomentReview(@PathVariable("momentid") int mid, @RequestBody CreateMomentReviewBody body) {
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
        Integer mrid = momentReviewService.addMomentReview(body, mid);
        Map<String, Integer> res = new HashMap<>();
        res.put("mrid", mrid);
        return mid != -1 ? Result.success(res) :
                Result.error("创建失败！");
    }

    @DeleteMapping("/{momentid}/reviews/{momentrid}")
    Result deleteMomentReview(@PathVariable("momentrid") int mrid) {
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
        return momentReviewService.deleteMomentReview(mrid) ? Result.success() : Result.error("删除moment失败");
    }

    @DeleteMapping("/{momentid}/reviews/{momentrid}/likes")
    Result deleteMomentReviewLike(@PathVariable("momentrid") int mrid) {
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
        return momentReviewService.deleteMomentReviewLike(mrid) ? Result.success() : Result.error("取消moment点赞失败");
    }

    @PostMapping("/{momentid}/reviews/{momentrid}/likes")
    Result addMomentReviewLike(@PathVariable("momentrid") int mrid) {
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
        return momentReviewService.addMomentReviewLike(mrid) ? Result.success() : Result.error("添加moment点赞失败");
    }

    private Integer getUidByToken() {
        return TokenMessage.getUidByToken(request);
    }
}
