package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/4/24.
 */
public class UpdateMediaRequest extends MediaRequest{
    private Long id;//主键

    private String isValid;//是否有效标识

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

    @ActionInput(notNull = false)
    public String getUmType() {
        return this.umType;
    }

    public void setUmType(String umType) {
        this.umType = umType;
    }

    @ActionInput(notNull = false)
    public String getUmUrl() {
        return umUrl;
    }

    public void setUmUrl(String umUrl) {
        this.umUrl = umUrl;
    }

    @ActionInput(notNull = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ActionInput(notNull = true)
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
}
