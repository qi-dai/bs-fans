package com.eden.fans.bs.domain.svo;

import com.eden.fans.bs.domain.enu.PostPraise;

import java.io.Serializable;

/**
 * 点赞用户
 * Created by Administrator on 2016/3/14.
 */
public class PraiseUser implements Serializable {
    /**
     *
     */
    private Integer userCode;

    /**
     *
     */
    private PostPraise praise;

    public Integer getUserCode() {
        return userCode;
    }

    public void setUserCode(Integer userCode) {
        this.userCode = userCode;
    }

    public PostPraise getPraise() {
        return praise;
    }

    public void setPraise(PostPraise praise) {
        this.praise = praise;
    }
}
