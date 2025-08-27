package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.request.RestaurantRequest;
import com.example.restaurantservice.api.response.RestaurantDTO;
import com.example.restaurantservice.domain.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .restaurantName(restaurant.getRestaurantName())
                .phone(restaurant.getPhone())
                .email(restaurant.getEmail())
                .ownerEmail(restaurant.getOwnerEmail())
                .address(restaurant.getAddress())
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
