package com.example.Notification;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class FirebaseJsonBuilder {

	public String build(List<String> tokens, Map<String, String> data) {
		return new JSONObject()
				.put("registration_ids", tokens)
				.put("data", data)
				.toString();
	}

	public String build(String token, Map<String, String> data) {
		return new JSONObject()
				.put("to", token)
				.put("data", data)
				.toString();
	}
}
