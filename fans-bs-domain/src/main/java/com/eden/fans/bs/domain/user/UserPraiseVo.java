package com.eden.fans.bs.domain.user;

import java.io.Serializable;

/**
 * Created by lirong5 on 2016/3/25.
 */
public class UserPraiseVo implements Serializable {
    private Long id;
    private Long fromUserCode;//发起赞用户编码
    private Long toUserCode;//被赞用户编码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(Long fromUserCode) {
        this.fromUserCode = fromUserCode;
    }

    public Long getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(Long toUserCode) {
        this.toUserCode = toUserCode;
    }
}
