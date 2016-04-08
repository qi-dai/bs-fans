package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.request.LoginRequest;
import com.eden.fans.bs.domain.response.LoginResponse;
import com.eden.fans.bs.domain.response.ServiceResponse;
import com.eden.fans.bs.domain.user.UserVo;

import java.util.List;

/**
 * Created by Mr.lee on 2016/3/15.
 */
public interface ICommonService {
    /**
     * 保存验证码到缓存
     * @param timestamp 时间戳 key
     * @param validCode 四位数随机验证码 value
     * */
    public void saveValidCode(String timestamp,String validCode);
    /**
     * 验证验证码是否正确
     * @param timestamp 时间戳 key
     * @param validCode 四位数随机验证码 value
     * */
    public boolean checkValidCode(String timestamp,String validCode);
    /**
     * 验证用户登录信息是否正确
     * @param loginRequest 登录验证需要参数
     * */
    public ServiceResponse<LoginResponse> checkUserInfo(LoginRequest loginRequest);

    /**
     * 根据用户userCode集合批量查询用户基础信息
     * */
    public List<UserVo> qryUserVosBatch(Long... userCode );
}
