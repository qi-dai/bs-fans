package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.UserScoreVo;

/**
 * Created by Administrator on 2016/5/7.
 */
public interface IUserScoreDao {
    public boolean addUserScore(UserScoreVo userScoreVo);
    public int sumUserScore(Long userCode);
}
