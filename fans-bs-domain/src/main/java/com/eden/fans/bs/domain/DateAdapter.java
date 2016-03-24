package com.eden.fans.bs.domain;

import com.eden.fans.bs.domain.enu.PostLevel;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/22.
 */
public class DateAdapter implements JsonDeserializer<Date>,JsonSerializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Date(jsonElement.getAsLong());
    }

    @Override
    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(date.getTime());
    }
}
