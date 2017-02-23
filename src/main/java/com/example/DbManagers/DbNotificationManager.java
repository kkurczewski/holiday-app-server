package com.example.DbManagers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class DbNotificationManager {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertToken(String token) {
		log.info("Inserting token: " + token);
		jdbcTemplate.update("INSERT INTO tokens(token) VALUES(?)", token);
	}

	public void updateToken(String newToken, String oldToken) {
		log.debug("Replaced token: " + oldToken + " with: " + newToken);
		jdbcTemplate.update("UPDATE tokens SET token=? WHERE token=?", newToken, oldToken);
	}

	public void deleteToken(String token) {
		log.debug("Deleted token: " + token);
		jdbcTemplate.update("DELETE FROM tokens WHERE token=?", token);
	}

	public List<String> getTokens() {
		RowMapper<String> rowMapper = (rs, rowNum) -> { 
			return rs.getString("token"); 
		};
		return jdbcTemplate.query("SELECT * FROM tokens", rowMapper);
	}

}
