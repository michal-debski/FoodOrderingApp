package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.request.RestaurantRequest;
import com.example.restaurantservice.api.response.RestaurantDTO;
import com.example.restaurantservice.domain.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantDTO map(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .phone(restaurant.getPhone())
                .email(restaurant.getEmail())
                .address(restaurant.getAddress())
                .build();
    }


    public Restaurant mapFromDto(RestaurantRequest restaurant) {
        return Restaurant.builder()
                .restaurantId(restaurant.restaurantId())
                .restaurantName(restaurant.restaurantName())
                .phone(restaurant.phone())
                .email(restaurant.email())
                .address(restaurant.address())
                .build();
    }
}
