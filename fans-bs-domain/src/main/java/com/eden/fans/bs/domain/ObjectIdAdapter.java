package com.eden.fans.bs.domain;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2016/3/22.
 */
public class ObjectIdAdapter implements JsonDeserializer<ObjectId>,JsonSerializer<ObjectId> {
    @Override
    public ObjectId deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new ObjectId(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(ObjectId objectId, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(objectId.toString());
    }
}
