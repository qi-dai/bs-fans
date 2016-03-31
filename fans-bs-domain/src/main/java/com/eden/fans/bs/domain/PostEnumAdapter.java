package com.eden.fans.bs.domain;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by IntelliJ IDEA.
 * Project:BS-FANS
 * User: ShengYanPeng
 * Date: 2016/3/31
 * Time: 10:09
 * To change this template use File | Settings | File Templates.
 */
public class PostEnumAdapter implements JsonSerializer<IPostEnum>{
    public JsonElement serialize(IPostEnum src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.returnName());
    }
}
