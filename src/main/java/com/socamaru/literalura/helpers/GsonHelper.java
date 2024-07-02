package com.socamaru.literalura.helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GsonHelper {
	public static JsonObject toJsonObject(String jsonString) {
		return JsonParser.parseString(jsonString).getAsJsonObject();
	}

	public static <T> T convertFromJson(String jsonFileDir, Type type) throws IOException {
		Gson gson = new GsonBuilder().create();
		String jsonFromFile = new String(Files.readAllBytes(Paths.get(jsonFileDir)));
		return gson.fromJson(jsonFromFile, type);
	}
}
