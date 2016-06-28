package com.eden.fans.bs.service.concurrent;

import com.eden.fans.bs.dao.IUserScoreDao;
import com.eden.fans.bs.domain.user.UserScoreVo;
import com.eden.fans.bs.service.IUserScoreService;

/**
 * Created by Administrator on 2016/6/27.
 */
public class ScoreAddRunable implements Runnable {
    private IUserScoreService userScoreService;
    private Long userCode;
    private int scoreType;
    @Override
    public void run() {
        try {
            userScoreService.addUserScore(userCode,scoreType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public IUserScoreService getUserScoreService() {
        return userScoreService;
    }

    public void setUserScoreService(IUserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }
}
