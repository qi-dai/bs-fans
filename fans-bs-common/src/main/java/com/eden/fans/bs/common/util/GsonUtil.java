package com.eden.fans.bs.common.util;

import com.eden.fans.bs.domain.DateTimeAdapter;
import com.eden.fans.bs.domain.IPostEnum;
import com.eden.fans.bs.domain.ObjectIdAdapter;
import com.eden.fans.bs.domain.PostEnumAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;

import java.util.Date;

/**
 * 适配器目前只提供序列化的输出，反序列化暂时不支持
 *
 * Created by Administrator on 2016/3/23.
 */
public class GsonUtil {
    private static Gson gson;
    static{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
        gsonBuilder.registerTypeAdapter(IPostEnum.class, new PostEnumAdapter());
        gson = gsonBuilder.create();
    }

    public static Gson getGson(){
        return gson;
    }
}
