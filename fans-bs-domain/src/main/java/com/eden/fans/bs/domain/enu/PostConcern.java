package com.eden.fans.bs.domain.enu;

/**
 * 关注
 * Created by Administrator on 2016/3/14.
 */
public enum PostConcern {
    COMMIT("关注",1),
    CANCEL("取消关注",0);
    private String name;
    private int value;
    private PostConcern(String name, int value){
        this.value = value;
        this.name = name;
    }
    public static String getName(int value) {
        for (PostConcern postConcern : PostConcern.values()) {
            if (postConcern.getValue() == value) {
                return postConcern.name;
            }
        }
        return null;
    }

    public static int getValue(String name) {
        for (PostConcern postConcern : PostConcern.values()){
            if (postConcern.getName() == name) {
                return postConcern.value;
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
