package com.eden.fans.bs.dao.util;

import com.eden.fans.bs.common.util.Constant;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/3/18.
 */
public class RedisCache {
    private static Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private StringRedisTemplate redisTemplate;

    private static Gson gson = new Gson();

    /**
     * 设置key:value,在固定时间后失效
     * */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);//设置缓存时间300秒自动失效
    }
    /**
     * 设置key:value,自定义失效时间后失效
     * */
    public void set(String key, String value,long times) {
        redisTemplate.opsForValue().set(key, value, times, TimeUnit.SECONDS);
    }

    /**
     * 设置key:value,自定义失效时间后失效
     * */
    public void set(String key, String value,long times,TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, times, timeUnit);
    }

    /**
     * 设置key:value,自定义失效时间后失效
     * */
    public String get(String key) {
       return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置对象到缓存
     * */
    public void set(String key,Object obj){
        redisTemplate.opsForValue().set(key, gson.toJson(obj));
    }
    /**
     * 从缓存中获取指定对象
     * */
    public <T> T  get(String key,Class<T> classOfT){
        return gson.fromJson(redisTemplate.opsForValue().get(key),classOfT);
    }

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
