package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserDao;
import com.eden.fans.bs.domain.UserVo;
import org.springframework.stereotype.Repository;

/**
 * Created by Mr.lee on 2016/3/15.
 */
@Repository
public class UserDaoImpl extends BaseDao<UserVo> implements IUserDao {
    @Override
    public UserVo qryUserVoByPhonePWD(UserVo userVo) {
        return super.queryForObject("UserMapper.selectOneUserVo",userVo);
    }

    @Override
    public boolean addUserRecord(UserVo userVo) {
        return super.insert("UserMapper.addUserInfo",userVo);
    }

    @Override
    public boolean addUserRecordDetail(UserVo userVo) {
        return super.insert("UserMapper.addUserInfoDetail",userVo);
    }
}
