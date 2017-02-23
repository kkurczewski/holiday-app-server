package com.example.Resources.form;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class Form {

	@Valid
	private MultipartFile[] images;

	@NotNull
	private String date;
	
	@Min(1)
	private int days;
	
	@Size(min = 3, max = 30)
	private String country;

	@Size(min = 3, max = 30)
	private String city;

	@Size(min = 3, max = 10000)
	private String description;

	@NotNull
	@Min(1)
	private BigDecimal price;

	@NotNull
	private String persons;
	
	@NotNull
	private	String meals;
	
	@NotNull
	private String climate;
	
	@NotNull
	private String transport;

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImages(MultipartFile[] images) {
		this.images = images;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPersons() {
		return persons;
	}

	public void setPersons(String persons) {
		this.persons = persons;
	}

	public String getMeals() {
		return meals;
	}

	public void setMeals(String meals) {
		this.meals = meals;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTransport() {
		return transport;
	}

	public void setTransport(String transport) {
		this.transport = transport;
	}
	
}
