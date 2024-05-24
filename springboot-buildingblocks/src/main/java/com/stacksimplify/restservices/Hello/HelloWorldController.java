package com.stacksimplify.restservices.Hello;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorldController {

    @GetMapping("/helloworld")
    public String helloWorld() {
        return "Hello World1";
    }

    @GetMapping("/helloworld-bean")
    public UserDetails helloWorldBean() {
        return new UserDetails("Ali", "Nando", "Madagascar");
    }

}