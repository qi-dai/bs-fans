package com.eden.fans.bs.dao;

/**
 * Created by Administrator on 2016/3/15.
 */
public interface ICommonDao {
    /**
     * 保存键值对到mongo
     * @param key  key
     * @param value 值
     * */
    public void saveKeyValue(String key,String value);

    /**
     * 从mongo获取缓存值
     * @param key  key
     * */
    public String getValue(String key);
}
