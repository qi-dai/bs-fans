package com.eden.fans.bs.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ScoreBoardVo  implements Serializable{
    private Long userCode;//用户编码
    private String userName;//用户昵称
    private Long totalScore;//用户总积分
    private Date updateTime;//最后更新时间

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
