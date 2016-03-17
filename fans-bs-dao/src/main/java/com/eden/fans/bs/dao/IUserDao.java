package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.UserVo;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface IUserDao {
    /**
     * 查询用户信息
     * @param phone 用户注册名/手机号
     * @param pwd  用户登录凭证  前端加密
     * */
    public UserVo qryUserVoByPhonePWD(String phone,String pwd);
}
