package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;

/**
 * Created by Administrator on 2016/4/24.
 */
public class QryUserMediaVos extends MediaRequest {
    private Integer pageNumber;
    private Integer currentPage;

    @ActionInput(notNull = false)
    public String getUmUrl() {
        return umUrl;
    }

    @ActionInput(notNull = true)
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    @ActionInput(notNull = true)
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
