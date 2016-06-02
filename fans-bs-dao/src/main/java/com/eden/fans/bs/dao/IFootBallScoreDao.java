package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.FootBallScoreVo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface IFootBallScoreDao {
    public boolean addFootBallScore(FootBallScoreVo footBallScoreVo);
    public List<FootBallScoreVo> qryFootBallScores();
}
