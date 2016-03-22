package com.eden.fans.bs.domain.user;

import java.util.Date;

/**
 * Created by Administrator on 2016/3/22.
 */
public class UserOperVo {
    private Long id;//自增主键
    private Long operCode;//操作人编码
    private String operType;//操作类型
    private Long userCode;//被操作用户编码
    private Date operTime;//操作时间
    private String operDesc;//操作描述

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOperCode() {
        return operCode;
    }

    public void setOperCode(Long operCode) {
        this.operCode = operCode;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public Date getOperTime() {
        return operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }

    public String getOperDesc() {
        return operDesc;
    }

    public void setOperDesc(String operDesc) {
        this.operDesc = operDesc;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }
}
