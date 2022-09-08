package com.edgarfrancisco.service;

import com.edgarfrancisco.exception.domain.EmailAlreadyExistsException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.exception.domain.UsernameAlreadyExistsException;
import com.edgarfrancisco.model.User;

public interface UserService {

    User register(String firstName, String lastName, String username, String email)
            throws UserNotFoundException, UsernameAlreadyExistsException, EmailAlreadyExistsException;

    public User findUserByUsername(String username);

}
