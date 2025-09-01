package com.example.restaurantservice.api.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.Set;



@Builder

public record RestaurantDTO(
        String restaurantName,
        @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
        String phone,
        @Email
        String email,
        String address,
        String ownerEmail,
        Set<RestaurantStreetDTO> restaurantStreets
) {


}
