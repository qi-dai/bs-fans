package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IUserScoreDao;
import com.eden.fans.bs.domain.user.ScoreBoardVo;
import com.eden.fans.bs.domain.user.UserScoreVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/7.
 */
@Repository
class UserScoreDaoImpl extends BaseDao<UserScoreVo> implements IUserScoreDao {
    @Override
    public boolean addUserScore(UserScoreVo userScoreVo) {
        return super.insert("UserScoreMapper.addUserScoreVo",userScoreVo);
    }

    @Override
    public int sumUserScore(Long userCode) {
        return super.queryCountForObject("UserScoreMapper.sumUserScore",userCode);
    }

    @Override
    public List<ScoreBoardVo> qryScoreBoard(Map<String, Object> params) {
        return null;
    }
}
