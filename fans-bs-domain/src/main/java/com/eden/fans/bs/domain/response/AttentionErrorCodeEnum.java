package com.eden.fans.bs.domain.response;

/**
 * Created by lirong5 on 2016/4/1.
 */
public enum AttentionErrorCodeEnum implements BaseCodeEnum {

    ADD_ATTENTION_ERROR("1021", "关注失败!", "插入关注记录到数据库失败！"),//第一次关注
    UPDATE_ATTENTION_ERROR("1022", "关注失败!", "关注记录不存在！"),//关注后取消关注，再关注
    UPDATE_ATTENTION_FAILED("1023", "取消关注失败!", "操作数据库发生异常！");

    public String code;
    public String msg;
    public String detail;

    private AttentionErrorCodeEnum() {
    }

    private AttentionErrorCodeEnum(String code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }
    @Override
    public String getCode() {
        return this.code;
    }
    @Override
    public String getMsg() {
        return this.msg;
    }
    @Override
    public String getDetail() {
        return this.detail;
    }
}
