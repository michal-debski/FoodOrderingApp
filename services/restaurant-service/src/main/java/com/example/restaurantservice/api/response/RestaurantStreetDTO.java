package com.example.restaurantservice.api.response;

import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.Street;
import lombok.Builder;


@Builder
public record RestaurantStreetDTO(
        Integer id,
        Street street,
        Restaurant restaurant
) {


}
