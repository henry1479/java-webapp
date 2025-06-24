package com.urise.webapp.util;

import com.google.gson.*;

import java.lang.reflect.Type;

public class GSONSectionAdapter<T> implements JsonSerializer<T>, JsonDeserializer<T> {
    private final static String CLASS_NAME = "CLASSNAME";
    private final static String INSTANCE = "INSTANCE";

    @Override
    public JsonElement serialize(T section, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASS_NAME, section.getClass().getName());
        JsonElement element = jsonSerializationContext.serialize(section);
        retValue.add(INSTANCE, element);
        return retValue;
    }

    @Override
    public T deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASS_NAME);
        String className = prim.getAsString();
        try {
            Class<?> classForSection = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonObject.get(INSTANCE), classForSection);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }

    }
}
