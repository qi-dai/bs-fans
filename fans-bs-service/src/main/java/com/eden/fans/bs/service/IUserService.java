package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.UserVo;
import com.eden.fans.bs.domain.response.ServiceResponse;

/**
 * Created by Administrator on 2016/3/20.
 */
public interface IUserService {
    /**
     * 简易-用户快速注册service
     * */
    public ServiceResponse<Boolean> addUserInfo(String phone,String password,String platform);
    /**
     * 用户填写详细资料注册service
     * */
    public ServiceResponse<Boolean> addUserInfoDetail(UserVo userVo);
}
