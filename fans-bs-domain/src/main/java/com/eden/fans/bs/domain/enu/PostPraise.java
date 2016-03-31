package com.eden.fans.bs.domain.enu;

import com.eden.fans.bs.domain.IPostEnum;

/**
 * 点赞
 * Created by Administrator on 2016/3/14.
 */
public enum PostPraise implements IPostEnum {
    COMMIT("点赞",1),
    CANCEL("取消点赞",0);
    private String name;
    private int value;
    private PostPraise(String name, int value){
        this.value = value;
        this.name = name;
    }

    @Override
    public String returnName() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
