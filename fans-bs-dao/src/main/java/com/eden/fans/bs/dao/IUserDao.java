package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.UserVo;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface IUserDao {
    /**
     * 查询用户信息
     * @param userVo 用户注册名/手机号 用户登录凭证  前端加密
     * */
    public UserVo qryUserVo(UserVo userVo);

    /**
     * 快速注册用户，新增用户信息记录
     * @param userVo 用户信息实体
     * @Return boolean 插入成功标记
     * */
    public boolean addUserRecord(UserVo userVo);

    /**
     * 详细注册用户，新增用户信息记录
     * @param userVo 用户信息实体
     * @Return boolean 插入成功标记
     * */
    public boolean addUserRecordDetail(UserVo userVo);

    /**
     * 更新用户信息
     * @param userVo 用户信息实体
     * @Return boolean 更新成功标记
     * */
    public boolean updateUserRecord(UserVo userVo,String sqlId);
}
