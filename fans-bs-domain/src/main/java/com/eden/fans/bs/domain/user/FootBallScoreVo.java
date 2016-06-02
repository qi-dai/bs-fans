package com.eden.fans.bs.domain.user;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/2.
 */
public class FootBallScoreVo implements Serializable {
    private Long id;
    private String name;
    private String phone;
    private int score;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
