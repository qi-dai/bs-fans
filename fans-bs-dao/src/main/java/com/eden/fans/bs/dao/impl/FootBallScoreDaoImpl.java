package com.eden.fans.bs.dao.impl;

import com.eden.fans.bs.dao.BaseDao;
import com.eden.fans.bs.dao.IFootBallScoreDao;
import com.eden.fans.bs.domain.user.FootBallScoreVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
@Repository
public class FootBallScoreDaoImpl extends BaseDao<FootBallScoreVo> implements IFootBallScoreDao {
    @Override
    public boolean addFootBallScore(FootBallScoreVo footBallScoreVo) {
        return super.insert("FootBallScoreMapper.addFootBallScoreVo",footBallScoreVo);
    }

    @Override
    public List<FootBallScoreVo> qryFootBallScores() {
        return super.queryForList("FootBallScoreMapper.qryFootBallScores",null);
    }
}
