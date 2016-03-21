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
    LOGIN_CHECK_FAILED("1001", "用户名不存在或密码错误", "用户信息校验不通过"),
    ADD_USER_FAILED("1002", "用户注册失败", "存储数据库发生异常"),
    USER_EXIST_FAILED("1003", "用户注册失败", "该用户已经注册！"),
    USER_CODE_ERROR("1004", "用户账号不存在或已注销", "用户编码格式不正确，不是纯数字！"),
    QRY_USER_INFO_ERROR("1005", "查询用户信息出错", "数据库查询发生异常！");
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
