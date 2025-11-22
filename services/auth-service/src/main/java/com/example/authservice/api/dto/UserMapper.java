package com.example.authservice.api.dto;

import com.example.authservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapFromRequest(RegistrationRequest registrationRequest) {
       return new User(
               registrationRequest.email(),
               registrationRequest.password(),
               registrationRequest.role()
       );
    }
}
