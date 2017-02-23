package com.example.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DbManagers.DbOffersFavoritesManager;
import com.example.DbManagers.DbUserManager;
import com.example.DbManagers.dto.Offer;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private DbUserManager dbUserManager;

	@Autowired
	private DbOffersFavoritesManager dbFavoritesManager;

	@GetMapping(value = "/favorites", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Offer> getConcreteJsonOffers(@RequestParam("user") int userId) {
		return dbFavoritesManager.getUserFavorites(userId);
	}
	
	@GetMapping(value = "/favorites/add")
	public void addFavorite(@RequestParam("user") int userId, @RequestParam("id") String offerId) {
		try {
			dbUserManager.addToFavorites(userId, offerId);
		} catch (DuplicateKeyException e) {
			throw new RuntimeException(e);
		}
	}
	
	@GetMapping(value = "/favorites/remove")
	public void removeFavorite(@RequestParam("user") int userId, @RequestParam("id") String offerId) {
		dbUserManager.removeFromFavorites(userId, offerId);
	}
	
	@GetMapping(value = "/register")
	public @ResponseBody int registerUser() {
		return dbUserManager.registerUser();
	}

}
