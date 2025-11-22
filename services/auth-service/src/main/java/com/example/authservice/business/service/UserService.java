package com.example.authservice.business.service;

import com.example.authservice.api.exceptions.UserAlreadyExistsException;
import com.example.authservice.business.dao.UserDAO;
import com.example.authservice.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        log.info("Trying to find user by email: {}", email);
        Optional<User> userOpt = userDAO.findByEmail(email);
        if (userOpt.isEmpty()) {
            System.out.println("User not found");
            return Optional.empty();
        }
        userOpt.ifPresent(u -> System.out.println("Found user: " + u.email()));
        return userOpt;
    }

    public void saveUser(User userFromRequest) {
        log.info("Trying to register user with email: {}", userFromRequest.email());
        Optional<User> optionalUser = userDAO.findByEmail(userFromRequest.email());
        if(optionalUser.isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userFromRequest.password());
            User userToSave = new User(userFromRequest.email(), encodedPassword, userFromRequest.role());
            userDAO.registerUser(userToSave);
        } else {
            log.error("User with password: {}, already exists", userFromRequest.email());
            throw new UserAlreadyExistsException("User already exists");
        }
    }
}
