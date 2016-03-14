package com.eden.fans.bs.domain.svo;

import java.io.Serializable;

/**
 * 帖子视频
 * Created by Administrator on 2016/3/13.
 */
public class PostVideo implements Serializable {

    /**
     * 当前视频在贴子的索引位置
     */
    private int index;

    /**
     * 视频地址
     */
    private String videoUrl;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
