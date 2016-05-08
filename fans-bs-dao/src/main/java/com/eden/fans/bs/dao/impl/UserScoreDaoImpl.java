package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserScoreDao;
import com.eden.fans.bs.domain.user.UserScoreVo;

/**
 * Created by Administrator on 2016/5/7.
 */
public class UserScoreDaoImpl extends BaseDao<UserScoreVo> implements IUserScoreDao {
    @Override
    public boolean addUserScore(UserScoreVo userScoreVo) {
        return super.insert("UserScoreMapper.addUserScoreVo",userScoreVo);
    }
}
