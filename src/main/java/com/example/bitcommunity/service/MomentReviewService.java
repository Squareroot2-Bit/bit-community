package com.example.bitcommunity.service;

import com.example.bitcommunity.json.body.CreateMomentReviewBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentReviewData;

import java.util.List;

public interface MomentReviewService {
    Boolean deleteMomentReview(Integer mrid);

    Boolean addMomentReviewLike(Integer mrid);

    Boolean deleteMomentReviewLike(Integer mrid);

    Integer addMomentReview(CreateMomentReviewBody createMomentReviewBody, Integer mid);
    List<MomentReviewData> getMomentReviewList(PageBody pageBody, Integer mid);
}
