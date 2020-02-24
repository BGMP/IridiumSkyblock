package com.iridium.iridiumskyblock.serializer.typeadapter;

import com.google.gson.*;
import com.iridium.iridiumskyblock.XBiome;

import java.lang.reflect.Type;

public class XBiomeTypeAdapter implements JsonSerializer<XBiome>, JsonDeserializer<XBiome> {

    @Override
    public JsonElement serialize(XBiome biome, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(biome.name());
    }


    @Override
    public XBiome deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        return XBiome.matchXBiome(jsonElement.getAsString());
    }
}