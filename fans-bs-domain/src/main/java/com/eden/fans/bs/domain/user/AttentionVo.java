package com.eden.fans.bs.domain.user;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lirong5 on 2016/3/21.
 * 用户关注记录表
 */
public class AttentionVo implements Serializable {

    private Long id;
    protected String attType;//类型，0 关注，-1 取消关注
    protected Long fromUserCode;//发起关注用户编码
    protected Long toUserCode;//被关注用户编码
    private Timestamp createTime;//创建时间
    private Timestamp updateTime;//最后更新时间
    private String headImgUrl;//用户头像url
    private String userName;//用户昵称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
