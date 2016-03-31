package com.eden.fans.bs.domain.enu;

import com.eden.fans.bs.domain.IPostEnum;

/**
 *帖子状态描述
 *
 * Created by Administrator on 2016/3/13.
 */
public enum PostStatus implements IPostEnum {
    CHECK("待审",1),
    NORMAL("正常",2),
    LOCK("锁定",3),
    DELETE("删除",4);

    private String name;
    private int value;

    private PostStatus(String name, int value){
        this.value = value;
        this.name = name;
    }

    public static String getName(int value){
        for(PostStatus status:PostStatus.values()){
            if(value == status.getValue()){
                return status.getName();
            }
        }
        return "";
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
