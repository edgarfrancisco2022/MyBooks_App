package com.edgarfrancisco.service;

import com.edgarfrancisco.exception.domain.EmailAlreadyExistsException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.exception.domain.UsernameAlreadyExistsException;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.repository.UserRepository;
import com.edgarfrancisco.security.UserPrincipal;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;

import static com.edgarfrancisco.constant.FileConstant.DEFAULT_USER_IMAGE_PATH;
import static com.edgarfrancisco.constant.UserImplConstant.*;
import static com.edgarfrancisco.enumeration.Role.ROLE_USER;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/*
    All security related source code based on the Udemy course
    JSON Web Token (JWT) with Spring Security And Angular
    https://www.udemy.com/course/jwt-springsecurity-angular/
*/
@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginAttemptService loginAttemptService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_USERNAME + username);
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        } else {
            validateLoginAttempt(user); // checks number of failed login attempts
            user.setDisplayLastLogin(user.getLastLoginDate());
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_USERNAME + username);
            return userPrincipal;
        }
    }

    private void validateLoginAttempt(User user) {
        if(!user.isLocked()) {
            if(loginAttemptService.hasExceededMaxAttempts(user.getUsername())) {
                user.setLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getUsername());
        }
    }

    public User register(String firstName, String lastName, String username, String email)
            throws UserNotFoundException, UsernameAlreadyExistsException, EmailAlreadyExistsException {

        validateNewUsernameAndEmail(EMPTY, username, email); //org.apache.commons.lang3

        User user = new User();
        String password = generatePassword();
        String userId = generateUserId();

        user.setUserId(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setProfileImageUrl(getTemporaryProfileImageUrl(username));
        user.setPassword(encodePassword(password));
        user.setJoinDate(new Date());
        user.setRole(ROLE_USER.name());
        user.setAuthorities(ROLE_USER.getAuthorities());
        user.setActive(true);
        user.setLocked(false);

        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        return user;
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail) throws UserNotFoundException, UsernameAlreadyExistsException, EmailAlreadyExistsException {
        User userByNewUsername = userRepository.findByUsername(newUsername);
        User userByNewEmail = userRepository.findByEmail(newEmail);

        if (userByNewUsername != null) {
            throw new UsernameAlreadyExistsException(USERNAME_ALREADY_EXISTS);
        }
        if (userByNewEmail != null) {
            throw new EmailAlreadyExistsException(EMAIL_ALREADY_EXISTS);
        }

        if (StringUtils.isNotBlank(currentUsername)) { //org.apache.commons.lang3
            User currentUser = userRepository.findByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + currentUsername);
            }
            return currentUser;
        } else {
            return null;
        }
    }

    private String getTemporaryProfileImageUrl(String username) {
        /*creates links based on the current HttpServletRequest.*/
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
    private String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
