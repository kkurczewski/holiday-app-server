package com.example.DbManagers.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class OfferRowMapper implements RowMapper<Offer> {

	@Autowired
	private UrlManager urlManager;

	@Override
	public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {

		String offerId = rs.getString("offer_id");

		List<String> galleryUrls = urlManager.getGalleryUrls(offerId);
		String descriptionUrl = urlManager.getDescriptionUrl(offerId);

		return new Offer(
				rs.getString("date_start"), 
				rs.getInt("days"), 
				rs.getString("person_count"), 
				rs.getString("meals"),
				rs.getString("transport"), 
				rs.getString("climate"), 
				rs.getString("country"), 
				rs.getString("city"),
				rs.getBigDecimal("price"), 
				descriptionUrl, 
				galleryUrls, 
				offerId, 
				rs.getFloat("map_latitude"), 
				rs.getFloat("map_longitude"))
		;
	}

}
