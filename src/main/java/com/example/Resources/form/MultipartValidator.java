package com.example.Resources.form;

import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

public class MultipartValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg) {
		return Form.class.equals(arg);
	}

	@Override
	public void validate(Object arg, Errors errors) {
		
		Form offerForm = (Form) arg;
		boolean isAllEmpty = true;
		
		for (MultipartFile image : offerForm.getImages()) {
			
			if (isAllEmpty && !image.isEmpty()) {
				isAllEmpty = false;
			}
			if (!image.isEmpty() && !isJpegExtension(image)) {
				errors.rejectValue("images", "image.notJPG");
			}
		}
		
		if (isAllEmpty) {
			errors.rejectValue("images", "image.empty");
		}
	}

	private boolean isJpegExtension(MultipartFile image) {	
		return MimeTypeUtils.IMAGE_JPEG_VALUE.equals(image.getContentType());
	}

}
