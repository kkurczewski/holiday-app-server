package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    }

    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
    
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource resources = new ResourceBundleMessageSource();
		resources.setBasenames(new String[] { "messages/messages" });
		return resources;
	}

	@Bean
	public EnumProvider enumValuesProvider() {
		return new EnumProvider();
	}
    
}