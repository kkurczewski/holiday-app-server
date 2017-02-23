package com.example.Notification;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NotificationController {

	@Autowired
	private NotificationManager notificationManager;

	@GetMapping("/notification/register")
	public @ResponseBody String invalidMethod() {
		return "Invalid method call: GET instead of POST";
	}
	
	@PostMapping("/notification/register")
	public void registerToken(@RequestParam String token) {
		notificationManager.insertToken(token);
	}

	@GetMapping("/notification/push")
	public @ResponseBody String pushNotification(@RequestParam String offerId) {

		List<String> tokens = notificationManager.getTokens();
		HashMap<String, String> data = new HashMap<>();
		data.put("offerId", offerId);

		notificationManager.sendPushNotification(tokens, data);
		
		return "Pushed successfully";
	}

}
