package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/4/1.
 * 查询关注自己的用户列表
 */
public class QryToAttRequest {
    private String token;
    private String phone;
    private long toUserCode;//被关注用户编码
    private int pageNumber;

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
    public long getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(long toUserCode) {
        this.toUserCode = toUserCode;
    }


    @Override
    public String toString(){
        return new StringBuilder().append("toUserCode:").append(toUserCode).toString();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
