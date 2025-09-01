package com.example.restaurantservice.domain;

import lombok.*;


@Builder
@With
@Data
@Setter
@Getter
public class RestaurantStreet {
    String id;
    Street street;
    String restaurantId;
}
