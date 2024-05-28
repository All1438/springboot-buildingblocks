package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;
import com.stacksimplify.restservices.services.UserService;

import jakarta.validation.constraints.Min;

@RestController
@Validated
@RequestMapping(value = "/hateoas/users")
public class UserHateoasController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // Get User By Id
    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));
            Long userId = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUserById(userId)).withSelfRel();
            user.add(selfLink);
            return EntityModel.of(user);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // Get All Users Method
    @GetMapping
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();

        List<EntityModel<User>> userEntities = allUsers.stream().map(user -> {
            Long userId = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getUserById(userId)).withSelfRel();
            user.add(selfLink);

            Link ordersLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId)).withRel("all-orders");
            user.add(ordersLink);

            return EntityModel.of(user);
        }).toList();

        Link selfLinkForGetAllUsers = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers()).withSelfRel();
        return CollectionModel.of(userEntities, selfLinkForGetAllUsers);
    }
}
