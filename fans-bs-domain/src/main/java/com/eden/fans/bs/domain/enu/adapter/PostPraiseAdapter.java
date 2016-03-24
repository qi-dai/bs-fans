package com.eden.fans.bs.domain.enu.adapter;

import com.eden.fans.bs.domain.enu.PostConcern;
import com.eden.fans.bs.domain.enu.PostPraise;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class PostPraiseAdapter implements JsonDeserializer<PostPraise>,JsonSerializer<PostPraise> {
    @Override
    public PostPraise deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return PostPraise.getPostPraise(jsonElement.getAsInt());
    }

    @Override
    public JsonElement serialize(PostPraise postPraise, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(postPraise.getValue());
    }
}
