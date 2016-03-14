package com.eden.fans.bs.domain.svo;

import java.io.Serializable;

/**
 * 帖子图片
 * Created by Administrator on 2016/3/13.
 */
public class PostImg implements Serializable {

    /**
     * 当前图片在贴子的索引位置
     */
    private int index;

    /**
     * 图片地址
     */
    private String imgUrl;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
