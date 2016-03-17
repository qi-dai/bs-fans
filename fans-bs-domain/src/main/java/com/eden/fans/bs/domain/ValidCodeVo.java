package com.eden.fans.bs.domain;

import java.lang.annotation.Documented;

/**
 * Created by Administrator on 2016/3/15.
 */
public class ValidCodeVo {
    private String key;
    private String value;

    public ValidCodeVo(String key,String value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
