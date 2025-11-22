package com.example.authservice.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record RegistrationRequest(
        @Email @NotEmpty String email,
        @Email @NotEmpty String password,
        String role
) {
}
