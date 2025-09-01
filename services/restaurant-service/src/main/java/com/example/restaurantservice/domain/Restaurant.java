package com.example.restaurantservice.domain;

import lombok.*;

import java.util.Set;

@Builder
@With
@Data
@Setter
@Getter
public class Restaurant {

    String restaurantName;
    String phone;
    String email;
    String address;
    String ownerEmail;
    Set<RestaurantStreet> restaurantStreets;

}
