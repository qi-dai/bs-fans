package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.ResetPwdRequest;
import com.eden.fans.bs.domain.response.UserDetailResponse;
import com.eden.fans.bs.domain.user.UserVo;
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

    /**
     * 根据用户编号查询详细资料
     * */
    public ServiceResponse<UserDetailResponse> qryUserInfo(Long userCode);

    /**
     * 根据用户编号更新详细资料
     * */
    public ServiceResponse<Boolean> updateUserInfo(UserVo userVo);

    /**
     * 设置管理员
     * 记录管理员操作记录
     * */
    public ServiceResponse<Boolean> setAdminRole(String adminUserCode,String targetUserCode);

    /**
     * 重设密码
     * */
    public ServiceResponse<Boolean> resetPwd(ResetPwdRequest resetPwdRequest);
}
