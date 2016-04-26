package com.eden.fans.bs.domain.response;

import com.eden.fans.bs.domain.Page;
import com.eden.fans.bs.domain.user.UserVo;

import java.util.List;

/**
 * Created by Administrator on 2016/4/27.
 */
public class UserListResponse {
    private List<UserVo> userVoList;
    private Page page;

    public List<UserVo> getUserVoList() {
        return userVoList;
    }

    public void setUserVoList(List<UserVo> userVoList) {
        this.userVoList = userVoList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
