package com.stacksimplify.restservices.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.entities.Views;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {

    // Autowire the UserService
    @Autowired
    private UserService userService;

    // Get UserById -- External
    @JsonView(Views.External.class) // @JsonView = permet d'afficher seulement les attribut dans entity qui contient un @JsonView(External)
    @GetMapping("/external/{id}") // l'url sera "/users/{id}"
    public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        // @Min(1) = pour dire que les nombres de valeur entrer doit être suppérieur à 1
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // Get UserById -- Internal
    @JsonView(Views.Internal.class) // @JsonView = permet d'afficher seulement les attribut dans entity qui contient un @JsonView même internal ou external
    @GetMapping("/internal/{id}") // l'url sera "/users/{id}"
    public Optional<User> getUserById2(@PathVariable("id") @Min(1) Long id) {
        // @Min(1) = pour dire que les nombres de valeur entrer doit être suppérieur à 1
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
