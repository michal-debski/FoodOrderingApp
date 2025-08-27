package com.example.restaurantservice.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class RestaurantStreet {
    String id;
    Street street;
    String restaurantId;
}
