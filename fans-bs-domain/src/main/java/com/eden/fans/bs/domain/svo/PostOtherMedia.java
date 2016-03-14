package com.eden.fans.bs.domain.svo;

import java.io.Serializable;

/**
 * 帖子的其他媒体
 * Created by Administrator on 2016/3/13.
 */
public class PostOtherMedia implements Serializable {

    /**
     * 当前媒体在贴子的索引位置
     */
    private int index;

    /**
     * 媒体地址
     */
    private String otherUrl;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOtherUrl() {
        return otherUrl;
    }

    public void setOtherUrl(String otherUrl) {
        this.otherUrl = otherUrl;
    }
}
