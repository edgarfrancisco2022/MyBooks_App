package com.edgarfrancisco.controller;

import com.edgarfrancisco.exception.ExceptionHandling;
import com.edgarfrancisco.exception.domain.EmailAlreadyExistsException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.exception.domain.UsernameAlreadyExistsException;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController extends ExceptionHandling {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException,
                                                                        UsernameAlreadyExistsException,
                                                                        EmailAlreadyExistsException {

        User newUser = userService.register(user.getFirstName(), user.getLastName(),
                                            user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
