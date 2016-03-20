package com.eden.fans.bs.domain.svo;

import com.eden.fans.bs.domain.enu.PostPraise;

import java.io.Serializable;
import java.util.Date;

/**
 * 点赞用户
 * Created by Administrator on 2016/3/14.
 */
public class PraiseUser implements Serializable {
    /**
     *用户编码
     */
    private Integer userCode;

    /**
     *1：点赞，0取消点赞
     */
    private int praise;

    /**
     * 点赞的时间
     */
    private Date time;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
