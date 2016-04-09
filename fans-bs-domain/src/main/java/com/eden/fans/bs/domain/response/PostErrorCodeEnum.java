package com.eden.fans.bs.domain.response;

/**
 * Created by Administrator on 2016/3/19.
 */
public enum PostErrorCodeEnum implements BaseCodeEnum {
    SUCCESS("3000", "成功", "成功"),
    CREATE_POST_SUCCESS("3001", "创建帖子成功", "创建帖子成功"),
    CREATE_POST_FAILED("-3001", "创建帖子失败", "创建帖子失败，请稍后再试"),
    CREATE_POST_AUTH_FAILED("-30011", "创建帖子失败", "只有管理员可以创建广告帖子"),
    PRAISE_POST_SUCCESS("3002", "点赞成功", "点赞成功"),
    PRAISE_POST_FAILED("-3002", "点赞失败", "点赞失败，请稍后再试"),
    CONCERN_POST_SUCCESS("3003", "关注成功", "关注成功"),
    CONCERN_POST_FAILED("-3003", "关注失败", "关注失败，请稍后再试"),
    REPLY_POST_SUCCESS("3004", "回帖成功", "回帖成功"),
    REPLY_POST_FAILED("-3004", "回帖失败", "回帖失败，请稍后再试"),
    UPDATE_POST_SUCCESS("3005", "更新帖子状态成功", "更新帖子状态成功"),
    UPDATE_POST_FAILED("-3005", "更新帖子状态失败", "更新帖子状态失败,请稍后再试"),
    UPDATE_POST_AUTH_FAILED("-30051", "更新帖子状态失败", "更新帖子状态失败，您不是帖子的创建人"),
    APPROVE_POST_SUCCESS("3007", "审批操作成功", "审批操作成功"),
    APPROVE_POST_FAILED("-3007", "审批操作失败", "审批操作失败失败,请稍后再试"),
    GET_APPROVAL_POST_SUCCESS("3006", "获取待审批的帖子成功", "获取待审批的帖子成功"),
    GET_APPROVAL_POST_FAILED("-3006", "获取待审批的帖子失败", "获取待审批的帖子失败，您不是管理员");
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
