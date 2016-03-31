package com.eden.fans.bs.common.util;

import com.eden.fans.bs.domain.DateTimeAdapter;
import com.eden.fans.bs.domain.TimestampAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 暂时没有更好的办法 不能在此处浪费过多的时间
 *
 * Created by Administrator on 2016/3/26.
 */
public class UserGsonUtil {
    private static Gson gson;
    static{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
        gsonBuilder.registerTypeAdapter(Timestamp.class, new TimestampAdapter());
        gson = gsonBuilder.create();
    }

    public static Gson getGson(){
        return gson;
    }
}
