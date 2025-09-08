package com.example.restaurantservice.infrastructure.repository.mapper;


import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.Street;
import com.example.restaurantservice.infrastructure.RestaurantEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RestaurantEntityMapper {


    public Restaurant mapFromEntity(RestaurantEntity entity){
        return Restaurant.builder()
                .restaurantName(entity.getRestaurantName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .ownerEmail(entity.getOwnerEmail())
                .streets(entity.getStreets() == null ? null : entity.getStreets().stream().map(
                        streetEntity ->
                                Street.builder()
                                        .streetId(streetEntity.getStreetId())
                                        .name(streetEntity.getName())
                                        .build()
                ).collect(Collectors.toSet()))
                .build();
    }


    public RestaurantEntity mapToEntity(Restaurant restaurant){
        return RestaurantEntity.builder()
                .restaurantName(restaurant.getRestaurantName())
                .email(restaurant.getEmail())
                .address(restaurant.getAddress())
                .phone(restaurant.getPhone())
                .ownerEmail(restaurant.getOwnerEmail())
                .build();
    }


}