package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.UserOperVo;

import java.util.List;

/**
 * Created by Administrator on 2016/3/22.
 */
public interface IOperUserDao {
    public List<UserOperVo> qryOperUserRecords(UserOperVo qryVo);
    public boolean addOperUserRecord(UserOperVo addVo);
}
