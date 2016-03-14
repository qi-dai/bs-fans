package com.eden.fans.bs.domain.enu;

/**
 *帖子级别描述
 *
 * Created by Administrator on 2016/3/13.
 */
public enum PostLevel {
    COMMON("普通",1),
    BOUTIQUE("精品",2),
    HIGH_GREEN("高亮绿",3),
    HIGH_RED("高亮红",4);

    private String name;
    private int value;


    private PostLevel(String name, int value){
        this.value = value;
        this.name = name;
    }

    public static String getName(int value) {
        for (PostLevel postType : PostLevel.values()) {
            if (postType.getValue() == value) {
                return postType.name;
            }
        }
        return null;
    }

    public static int getValue(String name) {
        for (PostLevel postType : PostLevel.values()){
            if (postType.getName() == name) {
                return postType.value;
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
