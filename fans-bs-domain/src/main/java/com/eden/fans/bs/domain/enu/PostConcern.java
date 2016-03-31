package com.eden.fans.bs.domain.enu;

import com.eden.fans.bs.domain.IPostEnum;

/**
 * 关注
 * Created by Administrator on 2016/3/14.
 */
public enum PostConcern implements IPostEnum {
    COMMIT("关注",1),
    CANCEL("取消关注",0);
    private String name;
    private int value;
    private PostConcern(String name, int value){
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
