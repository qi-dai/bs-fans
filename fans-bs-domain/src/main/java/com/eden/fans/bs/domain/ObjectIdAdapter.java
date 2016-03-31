package com.eden.fans.bs.domain;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ObjectIdAdapter implements JsonSerializer<ObjectId> {
    @Override
    public JsonElement serialize(ObjectId objectId, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(objectId.toString());
    }
}
