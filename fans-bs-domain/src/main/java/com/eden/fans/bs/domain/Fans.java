package com.eden.fans.bs.domain;

import java.io.Serializable;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
public class Fans implements Serializable{

    /**
     * 粉丝昵称
     */
    private String fansNickName;

    /**
     * 粉丝密码
     */
    private String fansPassword;

    /**
     * 粉丝的状态（0：不可用，1：可用）
     */
    private Integer fansStatus;

    public String getFansNickName() {
        return fansNickName;
    }

    public void setFansNickName(String fansNickName) {
        this.fansNickName = fansNickName;
    }

    public String getFansPassword() {
        return fansPassword;
    }

    public void setFansPassword(String fansPassword) {
        this.fansPassword = fansPassword;
    }

    public Integer getFansStatus() {
        return fansStatus;
    }

    public void setFansStatus(Integer fansStatus) {
        this.fansStatus = fansStatus;
    }
}
