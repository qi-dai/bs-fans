package com.eden.fans.bs.domain.request;

import com.eden.fans.bs.domain.annotation.ActionInput;
import com.eden.fans.bs.domain.user.AttentionVo;

/**
 * Created by lirong5 on 2016/4/1.
 */
public class AttentionRequest{
    private String token;
    private String phone;
    private long fromUserCode;//发起关注用户编码
    private long toUserCode;//被关注用户编码
    private boolean attentionFlag;//关注 true or 取消关注 false；

    @ActionInput(notNull = true)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @ActionInput(notNull = true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @ActionInput(notNull = true)
    public long getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(long fromUserCode) {
        this.fromUserCode = fromUserCode;
    }
    @ActionInput(notNull = true)
    public long getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(long toUserCode) {
        this.toUserCode = toUserCode;
    }


    @ActionInput(notNull = true)
    public boolean getAttentionFlag() {
        return attentionFlag;
    }

    public void setAttentionFlag(boolean attentionFlag) {
        this.attentionFlag = attentionFlag;
    }

    @Override
    public String toString(){
        return new StringBuilder().append("toUserCode:").append(toUserCode)
                .append(",fromUserCode:").append(fromUserCode).append(",attentionFlag:").append(this.attentionFlag).toString();
    }
}
