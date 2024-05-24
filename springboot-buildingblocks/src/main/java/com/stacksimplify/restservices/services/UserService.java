package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stacksimplify.restservices.entities.User;
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
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // getUserById Method
    public Optional<User> getUserById(Long id) { // Optional = pour représenter une valeur qui peut être présente ou
                                                 // absente.
        // pour éviter les erreurs courantes liées à la manipulation de valeur nulles
        Optional user = userRepository.findById(id);

        return user;
    }

    // updateUser By Id
    public User updateUserById(Long id, User user) {
        user.setId(id); // permet de mettre à jour l'utilisateur

        return userRepository.save(user);
    }

    // deleteUser By Id
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    // get user by username
    public User getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
