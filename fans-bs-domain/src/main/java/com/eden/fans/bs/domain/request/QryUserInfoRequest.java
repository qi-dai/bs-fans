package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/11.
 */
public class QryUserInfoRequest implements Serializable {
    private String token;
    private String phone;
    private Long userCode;
    private String goalPhone;
    private String appCode;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGoalPhone() {
        return goalPhone;
    }

    public void setGoalPhone(String goalPhone) {
        this.goalPhone = goalPhone;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }
}
