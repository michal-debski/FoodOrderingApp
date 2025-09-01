package com.example.restaurantservice.api.response;

import lombok.Builder;


@Builder
public record RestaurantStreetDTO(
        String id,
        StreetDTO street,
        String restaurantId
) {


}
