package com.example.authservice.api.controller;

import com.example.authservice.api.exceptions.UserAlreadyExistsException;
import com.example.authservice.api.dto.RegistrationRequest;
import com.example.authservice.api.dto.UserMapper;
import com.example.authservice.business.service.UserService;
import com.example.authservice.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/registration")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody RegistrationRequest registrationRequest
    ) {

        User userFromRequest = userMapper.mapFromRequest(registrationRequest);
        try {
            userService.saveUser(userFromRequest);
            log.info("New user was created with email: {}", userFromRequest.email());

        } catch (UserAlreadyExistsException ex) {
            log.warn("User with email {} already exists", userFromRequest.email());
            return ResponseEntity.status(409).body("User already exists");
        }
        return ResponseEntity.ok().build();
    }
}
