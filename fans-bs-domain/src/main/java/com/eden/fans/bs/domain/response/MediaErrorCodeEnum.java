package com.eden.fans.bs.domain.response;

import com.fasterxml.jackson.databind.deser.Deserializers;

/**
 * Created by Administrator on 2016/4/24.
 */
public enum  MediaErrorCodeEnum implements BaseCodeEnum {
    ADD_MEDIA_ERROR("1030", "保存多媒体信息失败!", "操作数据库发生异常！"),
    QRY_MEDIA_FAILED("1031", "查询多媒体列表失败!", "操作数据库发生异常！"),
    UPDATE_MEDIA_FAILED("1032", "更新多媒体信息失败!", "操作数据库发生异常！");

    public String code;
    public String msg;
    public String detail;

    private MediaErrorCodeEnum() {
    }

    private MediaErrorCodeEnum(String code, String msg, String detail) {
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
