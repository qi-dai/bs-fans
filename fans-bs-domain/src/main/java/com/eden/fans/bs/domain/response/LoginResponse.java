package com.eden.fans.bs.domain.response;

import java.io.Serializable;

/**
 * Created by lirong5 on 2016/3/22.
 */
public class LoginResponse implements Serializable {
    private String token;//登录成功返回认证token，唯一
    private int errorNum;//密码输入错误次数
    private boolean isSuccess;//是否登录成功

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
