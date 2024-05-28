package com.stacksimplify.restservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringbootBuildingblocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBuildingblocksApplication.class, args);
	}

	// configuration importante qui pris en charge le multilingue
	@Bean
	public LocaleResolver localeREsolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

	// configure la source des messages pour la gestion de l'internationalisation (i18n)
	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages"); // alors le "messages" sera le pardéfaut
		return messageSource;
	}

}
