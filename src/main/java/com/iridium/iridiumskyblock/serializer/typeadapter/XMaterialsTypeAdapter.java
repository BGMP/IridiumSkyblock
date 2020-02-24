package com.iridium.iridiumskyblock.serializer.typeadapter;

import com.google.gson.*;
import com.iridium.iridiumskyblock.XMaterial;

import java.lang.reflect.Type;

public class XMaterialsTypeAdapter implements JsonSerializer<XMaterial>, JsonDeserializer<XMaterial> {

    @Override
    public JsonElement serialize(XMaterial material, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(material.name());
    }


    @Override
    public XMaterial deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return XMaterial.valueOf(jsonElement.getAsString().replace("LEGACY_", ""));
    }
}