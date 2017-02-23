package com.example.DbManagers;

import java.util.List;

import com.example.DbManagers.dto.Offer;

public interface DbOffersFavoritesManager {
	List<Offer> getUserFavorites(int userId);
}
