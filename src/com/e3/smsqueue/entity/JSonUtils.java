package com.e3.smsqueue.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JSonUtils {

	public static String toJSON(List<SMS> smsPackage) {
		Gson gson = new Gson();
		String result = gson.toJson(smsPackage, new TypeToken<List<SMS>>() {}.getType());
		return result;
	}

	public static List<SMS> fromJSON(String json) {
		if (json == "") {
			return new ArrayList<SMS>();
		}
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<SMS>>() {}.getType());
	}

}
