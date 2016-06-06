package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/5.
 */
public class ScoreBoardRequest implements Serializable {
    private String token;
    private String phone;
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

}
