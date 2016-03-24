package com.eden.fans.bs.domain.enu.adapter;

import com.eden.fans.bs.domain.enu.PostStatus;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PostStatusAdapter implements JsonDeserializer<PostStatus>,JsonSerializer<PostStatus> {
    @Override
    public PostStatus deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return PostStatus.getPostStatus(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(PostStatus postStatus, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postStatus.getName());
    }
}
