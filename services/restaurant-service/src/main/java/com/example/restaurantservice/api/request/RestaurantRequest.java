package com.example.restaurantservice.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;


public record RestaurantRequest(
        String restaurantName,
        @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
        String phone,
        @Email
        String email,
        String address
) {
}
