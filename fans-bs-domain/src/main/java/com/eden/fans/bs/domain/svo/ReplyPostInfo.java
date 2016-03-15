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
