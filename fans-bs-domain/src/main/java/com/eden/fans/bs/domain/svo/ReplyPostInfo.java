package com.eden.fans.bs.domain.svo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 回帖模型描述
 * Created by Administrator on 2016/3/16.
 */
public class ReplyPostInfo implements Serializable{

    /**
     * userCode
     */
    private Long userCode;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户头像
     */
    private String headImgUrl;

    /**
     * 回帖的标题（类似XX回复XX）
     */
    private String title;

    /**
     * 回帖的内容中可能包含一些图片或者视频之类的链接
     */
    private List<String> medias;

    /**
     * 回帖的内容
     */
    private String content;

    /**
     * 回帖时间
     */
    private Date replyTime;

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getMedias() {
        return medias;
    }

    public void setMedias(List<String> medias) {
        this.medias = medias;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
}
