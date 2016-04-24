package com.eden.fans.bs.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/23.
 */
public class UserMediaVo implements Serializable{
    private Long id;
    private Long userCode;
    private String umType;
    private String umUrl;
    private Date createTime;
    private String isValid;
    private Date updateTime;

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

    public String getUmType() {
        return umType;
    }

    public void setUmType(String umType) {
        this.umType = umType;
    }

    public String getUmUrl() {
        return umUrl;
    }

    public void setUmUrl(String umUrl) {
        this.umUrl = umUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String isValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
