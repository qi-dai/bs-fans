package com.eden.fans.bs.domain.user;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/27.
 */
public class UserCountVo implements Serializable {
    private Long userCode;
    private int attentionNum;
    private int fansNum;
    private int contributeScore;
    private int postNum;
    private int imgNum;
    private int videoNum;

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
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
}
