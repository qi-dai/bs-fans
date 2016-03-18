package com.eden.fans.bs.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/19.
 */
public class LoginInfo implements Serializable {
    private String phone;
    private String devices;
    private String token;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDevices() {
        return devices;
    }

    public void setDevices(String devices) {
        this.devices = devices;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
