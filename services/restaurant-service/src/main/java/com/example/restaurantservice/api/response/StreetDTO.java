package com.example.restaurantservice.api.response;

import lombok.Builder;


@Builder
public record StreetDTO(
        Integer streetId,
        String name,
        RestaurantDTO restaurant
) {

}
