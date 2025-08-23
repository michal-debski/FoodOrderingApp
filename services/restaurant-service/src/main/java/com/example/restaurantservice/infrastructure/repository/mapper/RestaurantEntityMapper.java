package com.example.restaurantservice.infrastructure.repository.mapper;


import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.RestaurantStreet;
import com.example.restaurantservice.domain.Street;
import com.example.restaurantservice.infrastructure.RestaurantEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantEntityMapper {


    public Restaurant mapFromEntity(RestaurantEntity entity){
        return Restaurant.builder()
                .restaurantId(entity.getRestaurantId())
                .restaurantName(entity.getRestaurantName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .restaurantStreets(entity.getRestaurantStreets() == null ? null : entity.getRestaurantStreets().stream().map(
                        restaurantStreetEntity ->
                                RestaurantStreet.builder()
                                        .id(restaurantStreetEntity.getRestaurantStreetId())
                                        .street(Street.builder()
                                                .streetId(restaurantStreetEntity.getStreet().getStreetId())
                                                .name(restaurantStreetEntity.getStreet().getName())
                                                .build())
                                        .build()
                ).collect(Collectors.toSet()))
                .build();
    }


    public RestaurantEntity mapToEntity(Restaurant restaurant){
        return RestaurantEntity.builder()
                .restaurantId(restaurant.getRestaurantId())
                .restaurantName(restaurant.getRestaurantName())
                .email(restaurant.getEmail())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .restaurantStreets(null)
                .build();
    }


}