package com.example.Resources.form;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.Resources.ResourceManager;

@Controller
public class FormController {

	private static final int _USER_ID_ = 128;
	
	@Autowired
	private ResourceManager resourceManager;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new MultipartValidator());
	}

	@GetMapping("/offers/add")
	public String getForm(Form offerForm) {
		return "offerForm";
	}

	@PostMapping("/offers/add")
	public String postForm(@Valid Form offerForm, BindingResult result, RedirectAttributes responseMessage) {

		if (result.hasErrors()) {
			return "offerForm";
		}

		boolean isSuccess = resourceManager.tryAddOffer(offerForm, _USER_ID_);
		String response = isSuccess ? "Successfuly added new offer!" : "Failed to process request due to server error!";
		responseMessage.addFlashAttribute("response", response);
		
		return "redirect:/offers/add";
	}

}