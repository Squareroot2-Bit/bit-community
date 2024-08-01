package com.example.bitcommunity.service.impl;

import com.example.bitcommunity.json.body.CreateMomentReviewBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentReviewData;
import com.example.bitcommunity.mapper.MomentReviewLikeMapper;
import com.example.bitcommunity.mapper.MomentReviewMapper;
import com.example.bitcommunity.pojo.MomentReview;
import com.example.bitcommunity.pojo.MomentReviewLike;
import com.example.bitcommunity.pojo.sql.MomentReviewForBrowse;
import com.example.bitcommunity.service.MomentReviewService;
import com.example.bitcommunity.service.TokenMessage;
import com.example.bitcommunity.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class MomentReviewServiceImpl implements MomentReviewService {
    @Autowired
    private MomentReviewMapper momentReviewMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MomentReviewLikeMapper momentReviewLikeMapper;

    @Override
    public Boolean deleteMomentReview(Integer mrid) {
        Integer uid = getUidByToken();
        if(uid < 0) {
            return false;
        }
        return momentReviewMapper.delete(mrid, uid) == 1 ? true : false;
    }

    @Transactional
    @Override
    public Boolean addMomentReviewLike(Integer mrid) {
        Integer uid = getUidByToken();
        if(uid < 0) {
            return false;
        }
        if (momentReviewLikeMapper.insert(new MomentReviewLike(uid, mrid)) == 1 && momentReviewMapper.addLike(mrid) == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean deleteMomentReviewLike(Integer mrid) {
        Integer uid = getUidByToken();
        if(uid < 0) {
            return false;
        }
        if (momentReviewLikeMapper.delete(new MomentReviewLike(uid, mrid)) == 1 && momentReviewMapper.deleteLike(mrid) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Integer addMomentReview(CreateMomentReviewBody body, Integer mid) {
        MomentReview momentReview = new MomentReview();
        Integer uid = getUidByToken();
        if(uid < 0) {
            return -1;
        }
        momentReview.setMid(mid);
        momentReview.setReview(body.getContent());
        momentReview.setLikes(0);
        momentReview.setCreate_time(LocalDateTime.now());
        momentReview.setIs_deleted(0);
        momentReview.setUid(uid);
        if(body.getTo_momentrid() != null) {
            MomentReview mr = momentReviewMapper.select(body.getTo_momentrid());
            if(mr.getType() == 0) {
                momentReview.setType(1);
                momentReview.setDirect_mrid(mr.getMrid());
            } else if(mr.getType() == 1) {
                momentReview.setType(2);
                momentReview.setDirect_mrid(mr.getDirect_mrid());
            } else {
                momentReview.setType(2);
                momentReview.setDirect_mrid(mr.getDirect_mrid());
            }
            momentReview.setTo_uid(momentReview.getUid());
            momentReview.setTo_mrid(body.getTo_momentrid());
        } else { // 说明是一级评论
            momentReview.setType(0);
        }
        momentReviewMapper.insert(momentReview);
        return momentReview.getMrid();
    }

    @Override
    public List<MomentReviewData> getMomentReviewList(PageBody pageBody, Integer mid) {
        Integer uid = getUidByToken();
        if(uid < 0) {
            List<MomentReviewForBrowse> momentReviewForBrowseList = momentReviewMapper.selectDirectReviewNotLogin(pageBody.getOffset(), pageBody.getLimit(), mid);
            List<MomentReviewData> res = momentReviewForBrowseList.stream().map((item) -> {
                MomentReviewData resItem = new MomentReviewData(item);
                List<MomentReviewForBrowse> momentSubReviewList = momentReviewMapper.selectSubReviewNotLogin(0, 100, mid, item.getMrid());
                momentSubReviewList.forEach((subItem) -> {
                    resItem.addSubReview(subItem);
                });
                return resItem;
            }).toList();
            return res;
        }
        else {
            List<MomentReviewForBrowse> momentReviewForBrowseList = momentReviewMapper.selectDirectReview(pageBody.getOffset(), pageBody.getLimit(), mid, uid);
            List<MomentReviewData> res = momentReviewForBrowseList.stream().map((item) -> {
                MomentReviewData resItem = new MomentReviewData(item);
                List<MomentReviewForBrowse> momentSubReviewList = momentReviewMapper.selectSubReview(0, 100, mid, item.getMrid(), uid);
                momentSubReviewList.forEach((subItem) -> {
                    resItem.addSubReview(subItem);
                });
                return resItem;
            }).toList();
            return res;
        }
    }

    private Integer getUidByToken() {

        return TokenMessage.getUidByToken(request);
        /*String token = request.getHeader("Authorization");
        if (token != null && !token.split(" ")[1].isEmpty()) {
            Claims jwt = null;
            try {
                jwt = JwtUtil.parseJWT(token.split(" ")[1]);
            } catch (Exception e) {
                log.error("JWT解析异常 : {}", e.getMessage());
                return -1;
            }
            return jwt.get("uid", Integer.class);
        }
        return -1;*/
    }
}
