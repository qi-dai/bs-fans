package com.eden.fans.bs.domain.svo;

import java.io.Serializable;

/**
 * 帖子音乐
 * Created by Administrator on 2016/3/13.
 */
public class PostMusic implements Serializable {

    /**
     * 当前音乐在贴子的索引位置
     */
    private int index;

    /**
     * 音乐地址
     */
    private String musicUrl;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMusicUrl() {
        return musicUrl;
    }

    public void setMusicUrl(String musicUrl) {
        this.musicUrl = musicUrl;
    }
}
