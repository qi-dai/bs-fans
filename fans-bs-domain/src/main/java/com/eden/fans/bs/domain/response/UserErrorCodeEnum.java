package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/3/19.
 */
public enum UserErrorCodeEnum implements BaseCodeEnum {
    VALIDCODE_CHECK_FAILED("1000", "验证码输入错误", "验证码输入错误或校验失败或失效"),
    LOGIN_CHECK_FAILED("1001", "用户名不存在或密码错误", "用户不存在"),
    ADD_USER_FAILED("1002", "用户注册失败", "存储数据库发生异常"),
    USER_EXIST_FAILED("1003", "用户注册失败", "该用户已经注册！"),
    USER_CODE_ERROR("1004", "用户账号不存在或已注销", "用户编码格式不正确，不是纯数字！"),
    QRY_USER_INFO_FAILED("1009", "查询用户信息出错", "数据库查询发生异常！"),
    QRY_USER_INFO_ERROR("1010", "查询用户信息出错", "用户信息不存在或已注销！"),
    USER_NOTEXIST_ERROR("1011", "修改用户资料出错", "用户不存在或已注销！"),
    UPDATE_USER_INFO_FAILED("1012", "修改用户资料出错", "操作数据库发生异常！"),
    SET_ADMIN_ERROR("1013", "设置管理员出错!", "用户不存在或已注销！"),
    SET_ADMIN_FAILED("1014", "设置管理员出错!", "操作数据发生异常！"),
    RESET_PWD_FAILED("1015", "设置密码出错!", "操作数据发生异常！"),
    OLD_PWD_ERROR("1016", "原密码输入不正确，请重新输入!", "原密码不正确！"),
    RESET_PWD_ERROR("1017", "设置密码出错!", "用户不存在或已注销！"),
    UPDATE_USER_STATUS_ERROR("1018", "设置用户状态出错!", "用户不存在或已注销！"),
    UPDATE_USER_STATUS_FAILED("1019", "设置用户状态出错!", "操作数据库发生异常！");

    public String code;
    public String msg;
    public String detail;

    private UserErrorCodeEnum() {
    }

    private UserErrorCodeEnum(String code, String msg, String detail) {
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
