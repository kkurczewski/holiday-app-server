package com.example.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.DbManagers.dto.Offer;

@RestController
public class ResourceController {

	@Autowired
	private ResourceManager resourceManager;

	@GetMapping("/offers/remove")
	public @ResponseBody String getRemove(@RequestParam("id") String offerId) {
		resourceManager.removeOffer(offerId);
		return "Successfully removed offer: " + offerId;
	}
	
	@GetMapping("/offers/remove/all")
	public @ResponseBody String getRemove() {
		resourceManager.removeAllOffers();
		return "Cleared all data";
	}

	@GetMapping(value = "/offers", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Offer> getJsonOffers(
			@RequestParam(name = "user", defaultValue = "-1") int userId,
			@RequestParam(name = "date", defaultValue = "-1") String date,
			@RequestParam(name = "price", defaultValue = "-1") BigDecimal price, 
			@RequestParam(name = "persons", defaultValue = "-1") int personsCount, 
			@RequestParam(name = "meals", defaultValue = "-1") int mealsOption, 
			@RequestParam(name = "climate", defaultValue = "-1") int climateFlags) {

		return resourceManager.getOffersByParams(userId, date, price, personsCount, mealsOption, climateFlags);
	}
	
	@GetMapping(value = "/offers/{offerId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody Offer getConcreteOffer(@PathVariable String offerId) {
		return resourceManager.getOfferById(offerId);
	}
	
	@GetMapping(value = "/offers/{hashcode}/images/{fileName:.+\\.jpg}", produces = MediaType.IMAGE_JPEG_VALUE)
	public InputStreamResource getImage(@PathVariable String hashcode, 
										@PathVariable String fileName,
										@RequestParam(name = "w", defaultValue = "-1") int width, 
										@RequestParam(name = "h", defaultValue = "-1") int height) 
			throws IOException 
	{
		return new InputStreamResource(resourceManager.getImage(hashcode, fileName, width, height));
	}

	@GetMapping(value = "/offers/{hashcode}/description", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getDescription(@PathVariable String hashcode) 
			throws IOException 
	{
		BufferedReader bufferedReader = resourceManager.getDescription(hashcode);
		return bufferedReader.readLine();
	}
}
