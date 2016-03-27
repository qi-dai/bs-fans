package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/3/19.
 */
public enum PostErrorCodeEnum implements BaseCodeEnum {
    SUCCESS("3000", "成功", "成功"),
    CREATE_POST_SUCCESS("3001", "创建帖子成功", "创建帖子成功"),
    CREATE_POST_FAILED("-3001", "创建帖子失败", "系统出现问题，创建帖子失败"),
    PRAISE_POST_SUCCESS("3002", "点赞成功", "点赞成功"),
    PRAISE_POST_FAILED("-3002", "点赞失败", "系统出现问题，点赞失败"),
    CONCERN_POST_SUCCESS("3003", "关注成功", "关注成功"),
    CONCERN_POST_FAILED("-3003", "关注失败", "系统出现问题关注失败"),
    REPLY_POST_SUCCESS("3004", "回帖成功", "回帖成功"),
    REPLY_POST_FAILED("-3004", "回帖失败", "系统出现问题，回帖失败");
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
