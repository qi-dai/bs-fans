package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.AttentionRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;

/**
 * Created by lirong5 on 2016/4/1.
 */
public interface IUserAttentionService {
    /**
     * 设置/取消关注
     * */
    public ServiceResponse<Boolean> setAttention(AttentionRequest attentionRequest);
}
