package com.hotent.platform.service.util;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateFormatUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


public class DateSerializer implements JsonDeserializer<Date>,JsonSerializer<Date>{
	public Date deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException{
		try {
			Date rtDate = null;
			if(json.isJsonPrimitive()){
				String timeStr = json.getAsJsonPrimitive().getAsString();
				rtDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr);
			}else if(json.isJsonObject()){
				JsonObject jsonObj = json.getAsJsonObject();
				Long time = jsonObj.get("time").getAsLong();
				rtDate = new Date(time);
			}
			return rtDate;
		}
		catch (ParseException e) {
			throw new JsonParseException(e);
		} 
	}

	public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
		String s = DateFormatUtils.format(arg0, "yyyy-MM-dd HH:mm:ss");
		return new JsonPrimitive(s);
	}
}
