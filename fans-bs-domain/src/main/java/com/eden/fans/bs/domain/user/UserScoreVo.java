package com.eden.fans.bs.domain.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/7.
 */
public class UserScoreVo implements Serializable {
    private Long id;
    private Long userCode;
    private String scoreType;
    private int score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getScoreType() {
        return scoreType;
    }

    public void setScoreType(String scoreType) {
        this.scoreType = scoreType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
