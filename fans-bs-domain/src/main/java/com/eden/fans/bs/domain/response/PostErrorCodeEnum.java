package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/3/19.
 */
public enum PostErrorCodeEnum implements BaseCodeEnum {
    SUCCESS("3000", "成功", "成功"),
    CREATE_POST_SUCCESS("3001", "创建帖子成功", "创建帖子成功"),
    CREATE_POST_FAILD("-3001", "创建帖子失败", "系统出现问题，创建帖子失败")
    ;
    //RPC调用异常
    public String code;
    public String msg;
    public String detail;

    private PostErrorCodeEnum() {
    }

    private PostErrorCodeEnum(String code, String msg, String detail) {
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
