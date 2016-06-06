package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.PraiseRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;

/**
 * Created by Administrator on 2016/6/5.
 */
public interface IPraiseService {
    public ServiceResponse<Boolean> setUserPraise(PraiseRequest praiseRequest);
}
