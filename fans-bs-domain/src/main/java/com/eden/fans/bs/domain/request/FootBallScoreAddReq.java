package com.eden.fans.bs.domain.request;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/2.
 */
public class FootBallScoreAddReq implements Serializable {
    private String name;
    private int score;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
