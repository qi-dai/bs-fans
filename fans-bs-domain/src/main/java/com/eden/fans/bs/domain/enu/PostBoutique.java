package com.eden.fans.bs.domain.enu;

import com.eden.fans.bs.domain.IPostEnum;

/**
 *帖子加精
 *
 * Created by Administrator on 2016/3/13.
 */
public enum PostBoutique implements IPostEnum {
    BOUTIQUE("加精",1),
    NOBOUTIQUE("取消加精",0);

    private String name;
    private int value;


    private PostBoutique(String name, int value){
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
