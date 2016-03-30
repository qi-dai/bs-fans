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
    private Long userCode;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     *1：关注，0：取消关注
     */
    private PostConcern concern;

    /**
     * 点赞的时间
     */
    private Date time;

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PostConcern getConcern() {
        return concern;
    }

    public void setConcern(PostConcern concern) {
        this.concern = concern;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
