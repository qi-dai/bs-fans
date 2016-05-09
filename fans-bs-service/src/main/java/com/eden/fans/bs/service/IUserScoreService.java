package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.ScoreRecordRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;

/**
 * Created by Administrator on 2016/5/7.
 */
public interface IUserScoreService {
    public void addUserScore(Long userCode,int scoreType);
    public ServiceResponse<Boolean> addScoreRecord(ScoreRecordRequest scoreRecordRequest);
}
