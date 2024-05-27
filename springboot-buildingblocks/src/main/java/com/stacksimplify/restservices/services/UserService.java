package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;

@Service
public class UserService {

    @Autowired // @Autowired = facilite l'injection de dépendance
    private UserRepository userRepository;

    // getAllUsers Method
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // createUser Method
    public User createUser(User user) throws UserExistsException {
        // if user exist using username
        User existingUser = userRepository.findByUsername(user.getUsername());

        // if not exists throw UserExistsException
        if (existingUser != null) {
            throw new UserExistsException("User already exists in repository");
        }

        return userRepository.save(user);
    }

    // getUserById Method
    public Optional<User> getUserById(Long id) throws UserNotFoundException { // Optional = pour représenter une valeur
                                                                              // qui peut être présente ou//
                                                                              // absente.pour éviter les erreurs
                                                                              // courantes liées à la manipulation de
                                                                              // valeur nulles
        Optional user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found in user Repository");
        }
        return user;
    }

    // updateUser By Id
    public User updateUserById(Long id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User Not Found in user Repository, provide the correct user id");
        }

        user.setId(id); // permet de mettre à jour l'utilisateur

        return userRepository.save(user);
    }

    // deleteUser By Id
    public void deleteUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User Not Found in user Repository, provide the correct user id");
        }

        userRepository.deleteById(id);

    }

    // get user by username
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
