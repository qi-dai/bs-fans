package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/4/29.
 */
public enum DictErrorEnum implements BaseCodeEnum {

    QRY_MEDIA_ERROR("1040", "查询数据字典信息失败!", "操作数据库发生异常！");

    public String code;
    public String msg;
    public String detail;

    private DictErrorEnum() {
    }

    private DictErrorEnum(String code, String msg, String detail) {
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
