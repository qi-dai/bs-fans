package com.eden.fans.bs.domain.enu.adapter;

import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostStatus;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PostLevelAdapter implements JsonDeserializer<PostLevel>,JsonSerializer<PostLevel> {
    @Override
    public PostLevel deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return PostLevel.getPostLevel(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(PostLevel postLevel, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postLevel.getName());
    }
}
