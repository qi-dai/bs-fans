package com.eden.fans.bs.common.util;

import com.eden.fans.bs.domain.DateAdapter;
import com.eden.fans.bs.domain.enu.*;
import com.eden.fans.bs.domain.enu.adapter.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

/**
 * 暂时没有更好的办法 不能在此处浪费过多的时间
 *
 * Created by Administrator on 2016/3/23.
 */
public class GsonUtil {
    private static Gson gson;
    static{
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Date.class, new DateAdapter());
        gsonBuilder.registerTypeAdapter(PostStatus.class, new PostStatusAdapter());
        gsonBuilder.registerTypeAdapter(PostLevel.class, new PostLevelAdapter());
        gsonBuilder.registerTypeAdapter(PostType.class, new PostTypeAdapter());
        gsonBuilder.registerTypeAdapter(PostConcern.class, new PostConcernAdapter());
        gsonBuilder.registerTypeAdapter(PostPraise.class, new PostPraiseAdapter());
        gson = gsonBuilder.create();
    }

    public static Gson getGson(){
        return gson;
    }
}
