package com.example.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.DbManagers.DbOfferManager;
import com.example.DbManagers.dto.Offer;
import com.example.Resources.FilesManager.FilesManager;
import com.example.Resources.FilesManager.PathManager;
import com.example.Resources.ImagesManager.ImageManager;
import com.example.Resources.exceptions.RollbackException;
import com.example.Resources.form.Form;

@Component
public class ResourceManager {

	final private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ImageManager imageManager;

	@Autowired
	private FilesManager filesManager;

	@Autowired
	private DbOfferManager databaseManager;

	@Autowired
	private PathManager pathManager;

	public boolean tryAddOffer(Form form, int userId) {

		String offerId = generateOfferId(userId);

		try {
			createDirectories(offerId);
			imageManager.saveImagesOnDisk(form.getImages(), offerId);
			filesManager.saveDescription(form.getDescription(), offerId);
			databaseManager.insertOfferToDb(form.getDate(), form.getDays(), form.getPersons(), form.getMeals(),
					form.getTransport(), form.getClimate(), form.getCountry(), form.getCity(), form.getPrice(),
					offerId);

		} catch (IOException e) {
			log.error("Can't create resources: " + e);
			removeOfferFiles(offerId);
			return false;
		} catch (Exception e) {
			removeOfferFiles(offerId);
			throw e;
		}
		return true;
	}

	public List<Offer> getOffersByParams(int userId, String date, BigDecimal price, int personsCount, int mealsOption,
			int climateFlags) {
		return databaseManager.getOffers(userId, date, price, personsCount, mealsOption, climateFlags);
	}

	public Offer getOfferById(String offerId) {
		return databaseManager.getOfferById(offerId);
	}

	public List<Offer> getAllOffers() {
		return databaseManager.getAllOffers();
	}

	public void removeAllOffers() {
		List<Offer> offers = databaseManager.getAllOffers();
		for (Offer offer : offers) {
			String offerId = offer.getOfferId();
			removeOffer(offerId);
		}
	}

	public void removeOffer(String offerId) {
		databaseManager.deleteOffer(offerId);
		removeOfferFiles(offerId);
	}

	public InputStream getImage(String hashcode, String fileName, int width, int height) {
		try {
			return imageManager.getScaledImage(hashcode, fileName, width, height);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	public BufferedReader getDescription(String hashcode) {
		try {
			return filesManager.getDescription(hashcode);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		return null;
	}

	private String generateOfferId(int userId) {
		DateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
		return dateFormat.format(new Date()) + userId;
	}

	private void createDirectories(String hashcode) throws IOException {
		Path rootImageDir = pathManager.getImageDir(hashcode);
		Files.createDirectories(rootImageDir);
	}

	private void removeOfferFiles(String hashcode) {
		log.info("Removing offer: " + hashcode);
		try {
			Files.walkFileTree(pathManager.getRootDir(hashcode), new ResourcesDeleter());
		} catch (IOException e) {
			log.error("Removing offer: " + hashcode + " failed: " + e);
			throw new RollbackException(e);
		}
		log.info("Removed offer: " + hashcode);
	}

}