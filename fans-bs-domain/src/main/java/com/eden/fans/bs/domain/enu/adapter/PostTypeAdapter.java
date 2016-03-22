package com.eden.fans.bs.domain.enu.adapter;

import com.eden.fans.bs.domain.enu.PostLevel;
import com.eden.fans.bs.domain.enu.PostType;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PostTypeAdapter implements JsonDeserializer<PostType>,JsonSerializer<PostType> {
    @Override
    public PostType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return PostType.getPostType(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(PostType postType, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postType.getName());
    }
}
