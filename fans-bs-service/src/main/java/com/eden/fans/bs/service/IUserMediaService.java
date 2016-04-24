package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.QryUserMediaVos;
import com.eden.fans.bs.domain.request.MediaRequest;
import com.eden.fans.bs.domain.request.UpdateMediaRequest;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.UserMediaVo;

import java.util.List;

/**
 * Created by Administrator on 2016/4/24.
 */
public interface IUserMediaService {
    public ServiceResponse<Boolean> addMediaVo(MediaRequest saveMediaRequest);
    public ServiceResponse<List<UserMediaVo>> getUserMediaVos(QryUserMediaVos qryUserMediaVos);
    public ServiceResponse<Boolean> updateUserMediaVo(UpdateMediaRequest updateMediaRequest);
}
