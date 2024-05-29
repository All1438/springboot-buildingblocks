package com.stacksimplify.restservices.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
// import com.fasterxml.jackson.databind.ser.MappingJacksonValue;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/jacksonfilter/users")
@Validated
public class UserMappingJacksonControllers {

    @Autowired
    private UserService userService;

    // Get UserById
    @GetMapping("/{id}") // l'url sera "/users/{id}"
    // Une fonction qui permet d'afficher seulement les fields qui sont demander en fonction de l'id
    public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) { 
        // @Min(1) = pour dire que les nombres de valeur entrer doit être suppérieur à 1
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            Set<String> fields = new HashSet<String>(); // alors ces fields seulement qui sera afficher
            fields.add("userid");
            fields.add("username");
            fields.add("ssn");

            FilterProvider filterProvider = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            // "userFilter" qui sera la valeur clé pour se connecté avec l'entity dans JsonFilter

            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);
            return mapper;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

     // Get UserById - fields with @RequestParam or Query Params
     @GetMapping("/params/{id}") // /params/1?fields=id = permet d'afficher le id
     // ou ajouter dans le Key du Postman: Field et Value: id,username (Alors il affiche seulement l'id et l'username)
     // Une fonction qui permet d'afficher seulement les fields qui sont demander en fonction de l'id
     public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id, @RequestParam Set<String> fields) { 
         // @Min(1) = pour dire que les nombres de valeur entrer doit être suppérieur à 1
         try {
             Optional<User> userOptional = userService.getUserById(id);
             User user = userOptional.get();

            // créer une filter provider
            FilterProvider filters = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
             

             MappingJacksonValue mapper = new MappingJacksonValue(user); // alors ces fields seulement qui sera afficher
             
             mapper.setFilters(filters);
             return mapper;
 
         } catch (UserNotFoundException ex) {
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
         }
     }

}
