package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/3/19.
 */
public enum ResponseCode {
    SUCCESS("0","成功","成功"),
    FAILED("-1","失败","失败"),
    //系统异常
    SYSTEM_ERROR("999", "网络繁忙，请稍后再试","系统异常"), //
    // 参数校验
    PARAM_NULL("111", "网络繁忙，请稍后再试", "参数校验不通过"),
    LOGIN_CHECK_FAILED("1001", "用户名不存在或密码错误", "用户信息校验不通过");
    //RPC调用异常
    public String code;
    public String msg;
    public String detail;

    private ResponseCode() {
    }

    private ResponseCode(String code, String msg, String detail) {
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDetail() {
        return this.detail;
    }

}
