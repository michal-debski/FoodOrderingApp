package com.example.restaurantservice.domain;

import lombok.*;

import java.util.Set;

@Builder
@With
@Data
@Setter
@Getter
public class Restaurant {

    Integer restaurantId;
    String restaurantName;
    String phone;
    String email;
    String address;
    Set<RestaurantStreet> restaurantStreets;

}
