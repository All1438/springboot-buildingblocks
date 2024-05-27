package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Read All User
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Create User Method
    @PostMapping("/user")
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        // UriComponentsBuilder = est utilisé pour construire l'URL de la ressource
        // nouvellement créée
        try {
            userService.createUser(user);

            // Gestion des header
            HttpHeaders headers = new HttpHeaders(); // permet de créer un URL de la nouvelle ressource est ajouté au
                                                     // header, un objet pour stocké les headers
            // header = le header est comme une note spéciale sur l'enveloppe d'une lettre
            // qui donne des informations importantes sur la manière dont le message doit
            // être traité
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            // setLocation = défini les en-tête
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED); // ResponseEntity<Void> = un objet pour créer
                                                                          // la réponse HTTP

        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    // Get UserById
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable("id") Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // Update User By Id
    @PutMapping("/updateuser/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/deleteuser/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/user/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUserName(username);
    }
}
