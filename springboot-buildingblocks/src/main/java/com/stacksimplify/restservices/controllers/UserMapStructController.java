package com.stacksimplify.restservices.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stacksimplify.restservices.dtos.UserMsDto;
import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.mappers.UserMapper;
import com.stacksimplify.restservices.repositories.UserRepository;

@RestController
@RequestMapping("/mapstruct/users")
public class UserMapStructController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/") // return une request Get// récupère tous les utilisateur de la base de données
    public List<UserMsDto> getAllUserDto() {
        // convertit la liste d'entités User en une liste de DTO UserMsDto
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserMsDto getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        return userMapper.userToUserMsDto(user);
    }
}
