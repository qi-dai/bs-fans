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
     * 默认创建成功报文
     * */
    public ServiceResponse(){
        this.code = ResponseCode.SUCCESS.code;
        this.msg = ResponseCode.SUCCESS.msg;
        this.detail = ResponseCode.SUCCESS.detail;
    }
    /**
     *自定义返回枚举
     * */
    public ServiceResponse(ResponseCode responseCode){
        this.code = responseCode.code;
        this.msg = responseCode.msg;
        this.detail = responseCode.detail;
    }

    /**
     * 自定义返回带结果集
     * */
    public ServiceResponse(T result){
        this.code = ResponseCode.SUCCESS.code;
        this.msg = ResponseCode.SUCCESS.msg;
        this.detail = ResponseCode.SUCCESS.detail;
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
