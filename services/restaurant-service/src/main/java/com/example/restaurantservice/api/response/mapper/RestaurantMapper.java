package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.request.RestaurantRequest;
import com.example.restaurantservice.api.response.RestaurantDTO;
import com.example.restaurantservice.api.response.StreetDTO;
import com.example.restaurantservice.domain.Restaurant;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantMapper {

    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .phone(restaurant.getPhone())
                .email(restaurant.getEmail())
                .ownerEmail(restaurant.getOwnerEmail())
                .address(restaurant.getAddress())
                .streets(restaurant.getStreets() != null
                        ? restaurant.getStreets()
                        .stream()
                        .map(street ->
                                new StreetDTO(street.getStreetId(), street.getName())
                        )
                        .collect(Collectors.toSet())
                        : null)
                .build();
    }

    public Restaurant mapFromDto(RestaurantRequest restaurant, String ownerEmail) {
        return Restaurant.builder()
                .restaurantName(restaurant.restaurantName())
                .phone(restaurant.phone())
                .email(restaurant.email())
                .ownerEmail(ownerEmail)
                .address(restaurant.address())
                .build();
    }
}
