package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/4/26.
 */
public class QryUserListRequest {
    private String token;
    private String phone;
    private String qryType;
    private String role;
    private String status;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @ActionInput(notNull = true)
    public String getQryType() {
        return qryType;
    }

    public void setQryType(String qryType) {
        this.qryType = qryType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ActionInput(notNull = true)
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    @ActionInput(notNull = true)
    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
