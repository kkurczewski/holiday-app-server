package com.example.Notification;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.DbManagers.DbNotificationManager;

@Component
public class NotificationManager {

	private static final String FIREBASE_URL = "https://fcm.googleapis.com/fcm/send";
	private static final String SERVER_KEY = "key=AIza-...";

	private final Logger log = LoggerFactory.getLogger(getClass());

	private RestTemplate restTemplate = new RestTemplate();

	@Autowired
	private DbNotificationManager dbManager;

	public void insertToken(String token) {
		dbManager.insertToken(token);
	}

	public List<String> getTokens() {
		return dbManager.getTokens();
	}

	public void sendPushNotification(List<String> tokens, Map<String, String> data) {
		ResponseEntity<String> response = sendPushRequest(tokens, data);
		parseResponse(response.getBody(), tokens);
	}

	private ResponseEntity<String> sendPushRequest(List<String> tokens, Map<String, String> data) {
		
		String body = new FirebaseJsonBuilder().build(tokens, data);

		HttpHeaders header = new HttpHeaders();
		header.set("Authorization", SERVER_KEY);
		header.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(body, header);
		
		return restTemplate.postForEntity(FIREBASE_URL, request, String.class);
	}

	private void parseResponse(String response, List<String> tokens) {

		JSONObject root = new JSONObject(response);
		if (hasErrors(root)) {
			return;
		}

		JSONArray results = root.getJSONArray("results");
		for (int i = 0; i < results.length(); i++) {
			handleErrors(results.getJSONObject(i), i, tokens);
		}
	}

	private void handleErrors(JSONObject result, int index, List<String> tokens) {

		// docs: https://firebase.google.com/docs/cloud-messaging/server

		if (result.has("message_id")) {
			if (result.has("registration_id")) {
				String newToken = result.getString("registration_id");
				String oldToken = tokens.get(index);
				dbManager.updateToken(newToken, oldToken);
			}
		} else if (result.has("error")) {
			String error = result.getString("error");
			switch (error) {
				case "Unavailable":
					log.info("Token unavailable: " + tokens.get(index));
					break;
				case "NotRegistered":
					log.info("Token not registered: " + tokens.get(index) + ", deleting...");
					dbManager.deleteToken(tokens.get(index));
					break;
				default:
					log.error("Firebase error: " + error);
					break;
			}
		}
	}

	private boolean hasErrors(JSONObject jsonObject) {
		return jsonObject.getInt("failure") != 0 || jsonObject.getInt("canonical_ids") != 0;
	}

}
