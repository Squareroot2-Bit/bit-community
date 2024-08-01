package com.example.bitcommunity.service.impl;

import com.example.bitcommunity.json.body.CreateMomentBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentData;
import com.example.bitcommunity.mapper.MomentLikeMapper;
import com.example.bitcommunity.mapper.MomentMapper;
import com.example.bitcommunity.pojo.Moment;
import com.example.bitcommunity.pojo.MomentLike;
import com.example.bitcommunity.pojo.sql.MomentForBrowse;
import com.example.bitcommunity.service.MomentService;
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
public class MomentServiceImpl implements MomentService {
    @Autowired
    private MomentMapper momentMapper;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MomentLikeMapper momentLikeMapper;

    @Override
    public List<MomentBriefData> getMomentList(PageBody pageBody) {
        Integer uid = getUidByToken();
        List<MomentForBrowse> momentForBrowsesList = null;
        if (uid >= 0) {
            momentForBrowsesList = momentMapper.selectForBrowse(pageBody.getOffset(), pageBody.getLimit(), uid);
        } else {
            momentForBrowsesList = momentMapper.selectForBrowseNotLogin(pageBody.getOffset(), pageBody.getLimit());
        }
        return momentForBrowsesList.stream().map(item -> new MomentBriefData(item)).toList();
    }

    @Override
    public Integer addMoment(CreateMomentBody createMomentBody) {
        Moment moment = new Moment();
        Integer uid = getUidByToken();
        if (uid < 0) {
            return -1;
        }
        moment.setUid(uid);
        moment.setTitle(createMomentBody.getTitle());
        moment.setContent(createMomentBody.getContent());
        moment.setLikes(0);
        moment.setType(createMomentBody.getType());
        switch (createMomentBody.getType()) {
            case 1 -> moment.setImgs(createMomentBody.getMedia().toString());
            case 2 -> moment.setImgs(
                    "[" + (createMomentBody.getMedia().isEmpty() ?
                            "" :
                            createMomentBody.getMedia().get(0)) + "]");
            default -> moment.setImgs("");
        }
        moment.setCreate_time(LocalDateTime.now());
        moment.setUpdate_time(LocalDateTime.now());
        moment.setIs_deleted(0);
        moment.setCover_image_url(createMomentBody.getCover_image());
        momentMapper.insert(moment);
        return moment.getMid();
    }

    @Override
    public MomentData getMoment(int mid) {
        Integer uid = getUidByToken();
        MomentForBrowse momentForBrowses = null;
        if (uid >= 0) {
            momentForBrowses = momentMapper.selectByMid(mid, uid);
        } else {
            momentForBrowses = momentMapper.selectByMidNotLogin(mid);
        }
        if (momentForBrowses != null) {
            momentForBrowses.setTotal_review_num(momentMapper.selectReviewerNum(mid));
            return new MomentData(momentForBrowses);
        } else return null;
    }

    @Override
    public Boolean deleteMoment(int mid) {
        Integer uid = getUidByToken();
        if (uid < 0) {
            return false;
        }
        return momentMapper.delete(mid, uid) == 1 ? true : false;
    }

    @Transactional
    @Override
    public Boolean addMomentLike(int mid) {
        Integer uid = getUidByToken();
        if (uid < 0) {
            return false;
        }
        if (momentLikeMapper.insert(new MomentLike(uid, mid)) == 1 && momentMapper.addLike(mid) == 1) {
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean deleteMomentLike(int mid) {
        Integer uid = getUidByToken();
        if (uid < 0) {
            return false;
        }
        if (momentLikeMapper.delete(new MomentLike(uid, mid)) == 1 && momentMapper.deleteLike(mid) == 1) {
            return true;
        }
        return false;
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
