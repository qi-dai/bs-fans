package com.eden.fans.bs.domain.response;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/19.
 */
public class ServiceResponse<T> implements Serializable{
    private String code;
    private String msg;
    private String detail;
    private T result;

    /**
     * 默认创建成功报文，隐式创建
     * */
    public ServiceResponse(){
        this.code = SystemErrorEnum.SUCCESS.code;
        this.msg = SystemErrorEnum.SUCCESS.msg;
        this.detail = SystemErrorEnum.SUCCESS.detail;
    }

    /**
     * 显式创建：系统成功报文
     * */
    public static ServiceResponse successResponse() {
        return new ServiceResponse(SystemErrorEnum.SUCCESS);
    }

    /**
     * 显式创建：系统失败报文
     * */
    public static ServiceResponse failureResponse() {
        return new ServiceResponse(SystemErrorEnum.FAILED);
    }

    /**
     * 显式创建：接口逻辑处理错误
     * */
    public static ServiceResponse errorResponse() {
        return new ServiceResponse(SystemErrorEnum.SYSTEM_ERROR);
    }
    /**
     *自定义返回枚举
     * */
    public ServiceResponse(BaseCodeEnum baseCodeEnum){
        this.code = baseCodeEnum.getCode();
        this.msg = baseCodeEnum.getMsg();
        this.detail = baseCodeEnum.getDetail();
    }

    /**
     * 自定义返回带结果集
     * */
    public ServiceResponse(T result){
        this.code = SystemErrorEnum.SUCCESS.code;
        this.msg = SystemErrorEnum.SUCCESS.msg;
        this.detail = SystemErrorEnum.SUCCESS.detail;
        this.setResult(result);
    }

    public ServiceResponse(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public ServiceResponse(String code,String msg,String detail){
        this.code = code;
        this.msg = msg;
        this.detail = detail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
