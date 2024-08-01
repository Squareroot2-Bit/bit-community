package com.example.bitcommunity.service;

import com.example.bitcommunity.json.body.CreateMomentBody;
import com.example.bitcommunity.json.body.PageBody;
import com.example.bitcommunity.json.data.MomentBriefData;
import com.example.bitcommunity.json.data.MomentData;

import java.util.List;

public interface MomentService {
    List<MomentBriefData> getMomentList(PageBody pageBody);

    Integer addMoment(CreateMomentBody createMomentBody);

    MomentData getMoment(int mid);

    Boolean deleteMoment(int mid);

    Boolean addMomentLike(int mid);

    Boolean deleteMomentLike(int mid);
}
