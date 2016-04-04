package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/4/1.
 * 查询主动关注用户列表
 */
public class QryFromAttRequest{
    private String token;
    private String phone;
    protected long fromUserCode;//发起关注用户编码
    private int pageNumber;
    private int currentPage;

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
    public long getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(long fromUserCode) {
        this.fromUserCode = fromUserCode;
    }

    @Override
    public String toString(){
        return new StringBuilder().append(",fromUserCode:").append(fromUserCode).toString();
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
