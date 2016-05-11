package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.request.*;
import com.eden.fans.bs.domain.response.RegisterReponse;
import com.eden.fans.bs.domain.response.UserDetailResponse;
import com.eden.fans.bs.domain.response.UserListResponse;
import com.eden.fans.bs.domain.user.UserVo;
import com.eden.fans.bs.domain.response.ServiceResponse;

import java.util.List;

/**
 * Created by Administrator on 2016/3/20.
 */
public interface IUserService {
    /**
     * 简易-用户快速注册service
     * */
    public ServiceResponse<RegisterReponse> addUserInfo(RegisterRequest registerRequest);
    /**
     * 用户填写详细资料注册service
     * */
    public ServiceResponse<Boolean> addUserInfoDetail(UserVo userVo);

    /**
     * 根据用户编号查询详细资料
     * */
    public ServiceResponse<UserDetailResponse> qryUserInfo(QryUserInfoRequest qryUserInfoRequest);

    /**
     * 根据用户编号更新详细资料
     * */
    public ServiceResponse<Boolean> updateUserInfo(UserVo userVo);

    /**
     * 设置管理员
     * 记录管理员操作记录
     * */
    public ServiceResponse<Boolean> setAdminRole(SetAdminRequest setAdminRequest);

    /**
     * 重设密码
     * */
    public ServiceResponse<Boolean> resetPwd(ResetPwdRequest resetPwdRequest);

    /**
     * 设置用户状态-需管理员权限才可操作
     * */
    public ServiceResponse<Boolean> updateUserStatus(StatusUpdateRequest StatusUpdateRequest);

    /**
     * 刷新用户缓存信息
     * */
    public ServiceResponse<Boolean> freshUserInfo(String phone);

    /**
     * 分页查询用户列表
     * */
    public ServiceResponse<UserListResponse> qryUserVos(QryUserListRequest qryUserListRequest);

    /**
     * 获取用户基本信息-内部公共服务
     * */
    public UserVo getUserVo(String phone);

}
