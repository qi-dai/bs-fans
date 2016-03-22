package com.eden.fans.bs.domain.response;

import com.eden.fans.bs.domain.user.UserVo;

/**
 * Created by Administrator on 2016/3/22.
 */
public class UserDetailResponse {
    private UserVo userVo;
    private int attentionNum;
    private int fansNum;

    public UserVo getUserVo() {
        return userVo;
    }

    public void setUserVo(UserVo userVo) {
        this.userVo = userVo;
    }

    public int getAttentionNum() {
        return attentionNum;
    }

    public void setAttentionNum(int attentionNum) {
        this.attentionNum = attentionNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
}
