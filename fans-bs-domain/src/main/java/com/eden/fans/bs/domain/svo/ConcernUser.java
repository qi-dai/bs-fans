package com.eden.fans.bs.domain.svo;

import com.eden.fans.bs.domain.enu.PostConcern;
import com.eden.fans.bs.domain.enu.PostPraise;

import java.io.Serializable;

/**
 * 关注用户
 * Created by Administrator on 2016/3/14.
 */
public class ConcernUser implements Serializable {
    /**
     *
     */
    private Integer userCode;

    /**
     *
     */
    private PostConcern concern;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public PostConcern getConcern() {
        return concern;
    }

    public void setConcern(PostConcern concern) {
        this.concern = concern;
    }
}
