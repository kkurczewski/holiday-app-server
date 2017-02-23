package com.example.DbManagers.dto;

import java.math.BigDecimal;
import java.util.List;

public class Offer {

	final private String offerId;
	final private String dateStart;
	final private int days;
	final private String personCount;
	final private String mealsPreference;
	final private String transportType;
	final private String climateType;
	final private String country;
	final private String city;
	final private BigDecimal price;
	final private String descriptionUrl;
	final private List<String> galleryUrls;
	final private float mapLatitude;
	final private float mapLongitude;
	
	public Offer(String dateStart, int days, String personCount, String mealsPreference,
			String transportType, String climateType, String country, String city, BigDecimal price,
			String descriptionUrl, List<String> galleryUrls, String offerId, float latitude, float longitude) {
		
		this.offerId = offerId;
		this.dateStart = dateStart;
		this.days = days;
		this.personCount = personCount;
		this.mealsPreference = mealsPreference;
		this.transportType = transportType;
		this.climateType = climateType;
		this.country = country;
		this.city = city;
		this.price = price;
		this.descriptionUrl = descriptionUrl;
		this.galleryUrls = galleryUrls;
		this.mapLatitude = latitude;
		this.mapLongitude = longitude;
	}

	public String getOfferId() {
		return offerId;
	}
	
	public String getDateStart() {
		return dateStart;
	}

	public int getDays() {
		return days;
	}
	
	public String getPersonCount() {
		return personCount;
	}

	public String getMealsPreference() {
		return mealsPreference;
	}

	public String getTransportType() {
		return transportType;
	}

	public String getClimateType() {
		return climateType;
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getDescriptionUrl() {
		return descriptionUrl;
	}

	public List<String> getGalleryUrls() {
		return galleryUrls;
	}

	public float getMapLatitude() {
		return mapLatitude;
	}

	public float getMapLongitude() {
		return mapLongitude;
	}

}
