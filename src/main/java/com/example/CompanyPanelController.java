package com.example;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.DbManagers.DbOfferManager;
import com.example.DbManagers.dto.Offer;

@Controller
public class CompanyPanelController {

	private final int RECORD_PER_PAGE = 12;

	@Autowired
	private DbOfferManager dbManager;

	@GetMapping("/offers/panel")
	public String getOfferPanel(Model model, @RequestParam(defaultValue = "1") int page) {

		int offset = (page - 1) * RECORD_PER_PAGE;
		List<Offer> offers = dbManager.getOffers(offset, RECORD_PER_PAGE);
		int countOfAllOffers = dbManager.getCountOfAllOffers();
		
		model.addAttribute("countOfAllOffers", countOfAllOffers);
		model.addAttribute("offers", offers);
		model.addAttribute("page", page);
		model.addAttribute("recordPerPage", RECORD_PER_PAGE);
		
		return "myOffers";
	}

}