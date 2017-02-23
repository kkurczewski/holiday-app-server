package com.example.DbManagers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbUserManager {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void addToFavorites(int userId, String offerId) {
		jdbcTemplate.update("INSERT INTO favorites(user_id, offer_id) "
				+ "VALUES(?, (SELECT id FROM offers WHERE offer_id = ?))", userId, offerId);
	}
	
	public void removeFromFavorites(int userId, String offerId) {
		jdbcTemplate.update("DELETE FROM favorites WHERE user_id=? AND offer_id=(SELECT id FROM offers WHERE offer_id = ?)", userId, offerId);
	}

	public int registerUser() {
		jdbcTemplate.update("INSERT INTO users VALUES(null)");
		return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
	}

}
