package com.eden.fans.bs.service;

import com.eden.fans.bs.domain.Fans;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
public interface IFansService {

    /**
     * mysql数据库测试
     */
    public void test();

    public Fans findById(String key,Integer code);

    public void addFans(Fans fans,Integer code);
}
