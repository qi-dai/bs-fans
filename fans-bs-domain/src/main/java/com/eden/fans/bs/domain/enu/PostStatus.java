package com.eden.fans.bs.domain.enu;

/**
 *贴纸类型描述
 *
 * Created by Administrator on 2016/3/13.
 */
public enum PostStatus{
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

    public static String getName(int value) {
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.getValue() == value) {
                return postStatus.name;
            }
        }
        return null;
    }

    public static PostStatus getPostStatus(String name) {
        PostStatus tmpStatus = PostStatus.CHECK;
        for (PostStatus postStatus : PostStatus.values()) {
            if (postStatus.getName().equals(name)) {
                return postStatus;
            }
        }
        return tmpStatus;
    }

    public static int getValue(String name) {
        for (PostStatus postStatus : PostStatus.values()){
            if (postStatus.getName().equals(name)) {
                return postStatus.value;
            }
        }
        return 0;
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
