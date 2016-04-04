package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.AttentionRequest;
import com.eden.fans.bs.domain.request.QryFromAttRequest;
import com.eden.fans.bs.domain.request.QryToAttRequest;
import com.eden.fans.bs.domain.response.AttentionResponse;
import com.eden.fans.bs.domain.response.ServiceResponse;

/**
 * Created by lirong5 on 2016/4/1.
 */
public interface IUserAttentionService {
    /**
     * 设置/取消关注
     * */
    public ServiceResponse<Boolean> setAttention(AttentionRequest attentionRequest);
    /**
     * 查询主动发起关注列表-查询主动关注
     * */
    public ServiceResponse<AttentionResponse> getFromAttentions(QryFromAttRequest qryFromAttRequest);
    /**
     * 查询被关注列表-查询被关注
     * */
    public ServiceResponse<AttentionResponse> getToAttentions(QryToAttRequest qryToAttRequest);
}
