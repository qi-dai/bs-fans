package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.dict.DictVo;
import com.eden.fans.bs.domain.request.DictRequest;
import com.eden.fans.bs.domain.response.DictPageResponse;
import com.eden.fans.bs.domain.response.ServiceResponse;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/29.
 */
public interface IDictService {
    public ServiceResponse<Boolean>  addDictVo(DictVo dictVo);
    public ServiceResponse<List<DictVo>> queryDictVos(DictRequest dictRequest);
    public ServiceResponse<Boolean> updateDictVo(DictVo dictVo);
    public ServiceResponse<DictPageResponse> queryDictVosByPage(Map<String,Object> params);
}
