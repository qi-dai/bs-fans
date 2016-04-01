package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/3/28.
 */
public class SetAdminRequest {
    private String token;//管理员登录凭证
    private String phone;//管理员手机号码
    private Long userCode;//待设管理员编码
    private boolean type;//设置 true or 取消 false

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
    public boolean getType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
