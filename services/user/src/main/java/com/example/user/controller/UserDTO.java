package com.example.user.controller;

import lombok.Builder;

@Builder

public record UserDTO(
        Integer userId,
        String name,
        String surname,
        String phone,
        String email
) {
}
