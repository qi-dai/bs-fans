package com.eden.fans.bs.domain.response;

/**
 * Created by lirong5 on 2016/3/24.
 */
public enum SystemErrorEnum implements BaseCodeEnum {
    SUCCESS("0","成功","成功"),
    FAILED("-1","失败","失败"),
    ILLEGAL_REQUEST("-99", "response failed!","this illegal request!"),//拦截器返回中文有乱码。o(╯□╰)o
    //系统异常
    SYSTEM_ERROR("999", "网络繁忙，请稍后再试","系统异常"), //
    // 参数校验
    PARAM_NULL("111", "网络繁忙，请稍后再试", "参数校验不通过");
    public String code;
    public String msg;
    public String detail;

    private SystemErrorEnum() {
    }

    private SystemErrorEnum(String code, String msg, String detail) {
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
