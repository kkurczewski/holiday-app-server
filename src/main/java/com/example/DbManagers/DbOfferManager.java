package com.example.DbManagers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.EnumProvider;
import com.example.DbManagers.dto.Offer;
import com.example.DbManagers.dto.OfferRowMapper;

@Component
public class DbOfferManager implements DbOffersFavoritesManager {

	@Autowired
	private OfferRowMapper rowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void insertOfferToDb(String date, int days, String persons, String meals, String transport, String climate,
			String country, String city, BigDecimal price, String hashcode)
	{
		final String insertQuery = "INSERT INTO offers(date_start, days, person_count, meals, "
				+ "transport, climate, country, city, price, offer_id) VALUES(?,?,?,?,?,?,?,?,?,?)";

		jdbcTemplate.update(insertQuery, date, days, persons, meals, transport, climate, country, city, price,
				hashcode);
	}

	public List<Offer> getOffers(int userId, String sqlDate, BigDecimal price, int personsFlag, int mealsFlag,
			int climateFlag) {
		String excludeFavoritesQuery = String.format(" LEFT JOIN favorites ON offers.id = favorites.offer_id "
				+ "AND favorites.user_id = %d WHERE favorites.offer_id IS NULL", userId);

		String sqlQuery = "SELECT * FROM offers" + excludeFavoritesQuery;
		sqlQuery += getQueryParameters(sqlDate, price, personsFlag, mealsFlag, climateFlag);

		return jdbcTemplate.query(sqlQuery, rowMapper);
	}

	public List<Offer> getAllOffers() {
		return jdbcTemplate.query("SELECT * FROM offers", rowMapper);
	}
	
	public int getCountOfAllOffers() {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM offers", Integer.class);
	}
	
	public List<Offer> getOffers(int offset, int limit) {
		return jdbcTemplate.query("SELECT * FROM offers ORDER BY id LIMIT ?,?", rowMapper, offset, limit);
	}
	
	public Offer getOfferById(String offerId) {
		return (Offer) jdbcTemplate.queryForObject("SELECT * FROM offers WHERE offer_id = ?", new Object[] { offerId },
				rowMapper);
	}

	public List<Offer> getUserFavorites(int userId) {
		return jdbcTemplate.query("SELECT offers.* FROM offers INNER JOIN favorites "
				+ "ON offers.id = favorites.offer_id " + "WHERE favorites.user_id =" + userId, rowMapper);
	}

	public void deleteOffer(String offerId) {
		jdbcTemplate.update("DELETE FROM offers WHERE offer_id=?", offerId);
	}

	private String getQueryParameters(String sqlDate, BigDecimal price, int personsIndex, int mealsIndex,
			int climateFlag) {

		String parametrizedQuery = "";
		if (sqlDate != null) {
			parametrizedQuery += String.format(" AND date_start>='%s'", sqlDate);
		}
		if (isPriceEmpty(price)) {
			parametrizedQuery += String.format(" AND price<=%s", price);
		}
		if (isIndexEmpty(personsIndex)) {
			parametrizedQuery += String.format(" AND person_count='%s'",
					EnumProvider.getPersonsOptions().get(personsIndex));
		}
		if (isIndexEmpty(mealsIndex)) {
			parametrizedQuery += String.format(" AND meals='%s'", EnumProvider.getMealsOptions().get(mealsIndex));
		}
		if (isFlagEmpty(climateFlag)) {
			boolean[] selection = getClimateSelectionFromFlag(climateFlag,
					EnumProvider.getClimateOptionsFlags().size());
			List<String> values = getSelectedClimateOptions(selection);
			parametrizedQuery += " AND (0";
			for (String value : values) {
				parametrizedQuery += String.format(" OR climate='%s'", value);
			}
			parametrizedQuery += ")";
		}

		return parametrizedQuery;
	}

	private boolean isPriceEmpty(BigDecimal price) {
		return price != null && price.intValue() != -1;
	}

	private boolean isIndexEmpty(int personsIndex) {
		return personsIndex != EnumProvider.EMPTY_INDEX_SELECTION;
	}

	private boolean isFlagEmpty(int climateFlag) {
		return climateFlag > EnumProvider.EMPTY_FLAG_SELECTION;
	}

	protected boolean[] getClimateSelectionFromFlag(int climateFlag, int bits) {
		boolean[] selection = new boolean[bits];
		for (int i = 0; i < bits; i++) {
			selection[i] = ((1 << i) & climateFlag) > 0;
		}
		return selection;
	}

	private List<String> getSelectedClimateOptions(boolean[] climateSelection) {

		List<String> allValues = EnumProvider.getClimateOptionsFlags();
		List<String> hitValues = new ArrayList<>();

		for (int i = 0; i < climateSelection.length; i++) {
			if (climateSelection[i]) {
				hitValues.add(allValues.get(i));
			}
		}
		return hitValues;
	}

}
