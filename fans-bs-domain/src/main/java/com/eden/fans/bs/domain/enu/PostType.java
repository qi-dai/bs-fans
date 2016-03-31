package com.eden.fans.bs.domain.enu;

import com.eden.fans.bs.domain.IPostEnum;

/**
 *帖子类型描述
 *
 * Created by Administrator on 2016/3/13.
 */
public enum PostType implements IPostEnum {
    TEXT_MESSAGE("消息",1),
    VIDEO("视频",2),
    MV("MV",3),
    MUSIC("音乐",4),
    ALBUM("相册",5),
    ITINERARY("行程",6),
    COMMONPOST("普通帖子",101),
    IMITATION_VIDEO("模仿视频",102),
    IMITATION_MUSIC("音乐模仿",103),
    DYNAMIC_GRAPH("动态图", 104);

    private String name;
    private int value;


    private PostType(String name,int value){
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
