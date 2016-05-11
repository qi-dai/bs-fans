package com.eden.fans.bs.domain.response;

import com.eden.fans.bs.domain.user.UserVo;

/**
 * Created by Administrator on 2016/3/22.
 */
public class UserDetailResponse {
    private UserVo userVo;
    private int attentionNum;
    private int fansNum;
    private int contributeScore;
    private int postNum;
    private int imgNum;
    private int videoNum;
    private boolean isAtt;

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

    public int getContributeScore() {
        return contributeScore;
    }

    public void setContributeScore(int contributeScore) {
        this.contributeScore = contributeScore;
    }

    public int getPostNum() {
        return postNum;
    }

    public void setPostNum(int postNum) {
        this.postNum = postNum;
    }

    public int getImgNum() {
        return imgNum;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public int getVideoNum() {
        return videoNum;
    }

    public void setVideoNum(int videoNum) {
        this.videoNum = videoNum;
    }

    public boolean isAtt() {
        return isAtt;
    }

    public void setIsAtt(boolean isAtt) {
        this.isAtt = isAtt;
    }
}
