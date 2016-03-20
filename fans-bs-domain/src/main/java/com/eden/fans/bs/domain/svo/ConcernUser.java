package com.eden.fans.bs.domain.svo;

import com.eden.fans.bs.domain.enu.PostConcern;
import com.eden.fans.bs.domain.enu.PostPraise;

import java.io.Serializable;
import java.util.Date;

/**
 * 关注用户
 * Created by Administrator on 2016/3/14.
 */
public class ConcernUser implements Serializable {
    /**
     *用户编码
     */
    private Integer userCode;

    /**
     *1：关注，0：取消关注
     */
    private int concern;

    /**
     * 关注的时间
     */
    private Date time;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public int getConcern() {
        return concern;
    }

    public void setConcern(int concern) {
        this.concern = concern;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
