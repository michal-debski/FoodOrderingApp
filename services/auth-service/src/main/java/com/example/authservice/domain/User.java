package com.example.authservice.domain;

public record User (
        String email,
        String password,
        String role
) {

}
