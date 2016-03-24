package com.eden.fans.bs.domain.enu.adapter;

import com.eden.fans.bs.domain.enu.PostConcern;
import com.eden.fans.bs.domain.enu.PostLevel;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PostConcernAdapter implements JsonDeserializer<PostConcern>,JsonSerializer<PostConcern> {
    @Override
    public PostConcern deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return PostConcern.getPostConcern(jsonElement.getAsInt());
    }

    @Override
    public JsonElement serialize(PostConcern postConcern, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postConcern.getValue());
    }
}
