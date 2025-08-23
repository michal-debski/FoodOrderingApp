package com.example.restaurantservice.api.response;

import com.example.restaurantservice.domain.RestaurantStreet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.Set;



@Builder

public record RestaurantDTO(
        Integer restaurantId,
        String restaurantName,
        @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
        String phone,
        @Email
        String email,
        String address,
        Set<RestaurantStreet> restaurantStreets
) {


}
