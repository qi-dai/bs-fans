package com.eden.fans.bs.dao;

import com.eden.fans.bs.domain.Fans;

/**
 * Created by shengyanpeng on 2016/3/4.
 */
public interface IFansDao {

    /**
     * 测试mysql
     */
    public void test();

    public Fans findById(String key,Integer code);

    public void addFans(Fans fans,Integer code);


}
