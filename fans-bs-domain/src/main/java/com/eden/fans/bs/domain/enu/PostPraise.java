package com.eden.fans.bs.domain.enu;

/**
 * 点赞
 * Created by Administrator on 2016/3/14.
 */
public enum PostPraise {
    COMMIT("点赞",1),
    CANCEL("取消点赞",0);
    private String name;
    private int value;
    private PostPraise(String name, int value){
        this.value = value;
        this.name = name;
    }
    public static String getName(int value) {
        for (PostPraise postPraise : PostPraise.values()) {
            if (postPraise.getValue() == value) {
                return postPraise.name;
            }
        }
        return null;
    }

    public static PostPraise getPostPraise(int value) {
        PostPraise tmpPraise = PostPraise.COMMIT;
        for (PostPraise postPraise : PostPraise.values()) {
            if (postPraise.getValue() == value) {
                return tmpPraise;
            }
        }
        return tmpPraise;
    }

    public static int getValue(String name) {
        for (PostPraise postPraise : PostPraise.values()){
            if (postPraise.getName() == name) {
                return postPraise.value;
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
