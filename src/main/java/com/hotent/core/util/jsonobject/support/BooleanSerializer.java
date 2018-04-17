package com.hotent.core.util.jsonobject.support;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class BooleanSerializer implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {
	public Boolean deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		return json.toString().equals("\"1\"")
				? Boolean.valueOf(true)
				: (json.toString().equals("\"0\"") ? Boolean.valueOf(false) : Boolean.valueOf(json.getAsBoolean()));
	}

	public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2) {
		return new JsonPrimitive(arg0.toString());
	}
}