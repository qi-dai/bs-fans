package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.user.ScoreBoardVo;
import com.eden.fans.bs.domain.user.UserScoreVo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/7.
 */
public interface IUserScoreDao {
    public boolean addUserScore(UserScoreVo userScoreVo);
    public int sumUserScore(Long userCode);
    public List<ScoreBoardVo> qryScoreBoard(Map<String,Object> params);
}
