package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ScoreRecordRequest implements Serializable {
    private String token;
    private String phone;
    private Integer scoreType;
    @ActionInput(notNull = true)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @ActionInput(notNull = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ActionInput(notNull = true)
    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }
}
