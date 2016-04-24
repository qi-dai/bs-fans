package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/24.
 */
public class MediaRequest implements Serializable{
    public String token;//登录凭证
    public String phone;//手机号码
    public Long userCode;//当前用户编码
    public String umType;
    public String umUrl;

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
    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    @ActionInput(notNull = true)
    public String getUmType() {
        return this.umType;
    }

    public void setUmType(String umType) {
        this.umType = umType;
    }

    @ActionInput(notNull = true)
    public String getUmUrl() {
        return umUrl;
    }

    public void setUmUrl(String umUrl) {
        this.umUrl = umUrl;
    }
}
