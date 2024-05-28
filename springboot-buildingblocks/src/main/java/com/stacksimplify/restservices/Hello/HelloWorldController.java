package com.stacksimplify.restservices.Hello;

import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World1";
    }

    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("Ali", "Nando", "Madagascar");
    }
    
    // retourne un message internationalisé en fonction de la langue spécifiée dans l'en-tête "Accept-Language"
    @GetMapping("/hello-int")
    public String getMessagesInI18NFormat(@RequestHeader(name = "Accept-Language", required=false) String locale) {
        return messageSource.getMessage("label.hello", null, new Locale(locale));
    }

    @GetMapping("/hello-int2")
    public String getMessagesInI18NFormat2() {
        return messageSource.getMessage("label.hello", null, LocaleContextHolder.getLocale()); // "label.hello" c'est la message dans messages.properties
    }

}