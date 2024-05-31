package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.media.MediaType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@Tag(name = "User Management RESTful Services", description = "Controller for User Management Service") // name = le nom de la tag, description = la description de la tag
@RestController // @RestController = décorateur pour dire que c'est du Controller
@Validated
@RequestMapping(value = "/users") // @RequestMapping() = permet de définir une URL de base
public class UserController {

    @Autowired // @Autowired = simplifie la gestion des dépendances en injectant auto les beans appropriés dans les champs. ici c'est les dépendances de UserService
    private UserService userService;

    // Get All User
    @Operation(summary = "Retrieve list of users") // summary = un bref résumé de ce que fait l'opération
    // description = Une description détaillée de l'opération
    // response = Les réponses possibles de l'opération
    // opérationId = L'ID unique de l'opération
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Create User Method
    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<Void> createUser(@Parameter(description = "User informaiton for a new user to be created.") @Valid @RequestBody User user, UriComponentsBuilder builder) {
        // @Parameter = pour définir les paramètres de la méthode du contrôleur est l'annotation
        // @Valid = pour que les validation sont accépté dans entity
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
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getUserid()).toUri());
            // setLocation = défini les en-tête
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED); // ResponseEntity<Void> = un objet pour créer
                                                                          // la réponse HTTP

        } catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    // Get UserById
    @GetMapping("/{id}") // l'url sera "/users/{id}"
    public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        // @Min(1) = pour dire que les nombres de valeur entrer doit être suppérieur à 1
        try {
            Optional<User> userOptional = userService.getUserById(id);
            return userOptional;
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // Update User By Id
    @PutMapping("/{id}")
    public User updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            return userService.updateUserById(id, user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUserName(username);
    }
}
