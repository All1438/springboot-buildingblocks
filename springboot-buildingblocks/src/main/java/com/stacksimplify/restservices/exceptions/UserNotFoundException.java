package com.stacksimplify.restservices.exceptions;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message); // fait référence au constructeur de la classe mère
        // permet de transmettre le message
    }
}
