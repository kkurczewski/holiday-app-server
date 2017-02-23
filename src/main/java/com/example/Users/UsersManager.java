package com.example.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.DbManagers.DbOffersFavoritesManager;
import com.example.DbManagers.DbUserManager;
import com.example.DbManagers.dto.Offer;

@Component
public class UsersManager {

	@Autowired
	private DbUserManager dbUserManager;

	@Autowired
	private DbOffersFavoritesManager dbOfferManager;

	public List<Offer> getUserFavoriteOffers(int userId) {
		return dbOfferManager.getUserFavorites(userId);
	}

	public void addToFavorites(int userId, String offerId) {
		dbUserManager.addToFavorites(userId, offerId);
	}

	public void removeFromFavorites(int userId, String offerId) {
		dbUserManager.removeFromFavorites(userId, offerId);
	}
	
	public int registerUser() {
		return dbUserManager.registerUser();
	}
	
}
