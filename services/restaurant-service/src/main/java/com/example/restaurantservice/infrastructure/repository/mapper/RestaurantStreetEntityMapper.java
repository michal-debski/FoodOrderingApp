package com.example.restaurantservice.infrastructure.repository.mapper;


import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.RestaurantStreet;
import com.example.restaurantservice.domain.Street;
import com.example.restaurantservice.infrastructure.RestaurantEntity;
import com.example.restaurantservice.infrastructure.RestaurantStreetEntity;
import com.example.restaurantservice.infrastructure.StreetEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantStreetEntityMapper {

    public RestaurantStreet mapFromEntity(RestaurantStreetEntity entity){
        return RestaurantStreet.builder()
                .id(entity.getRestaurantStreetId())
                .restaurant(Restaurant.builder()
                        .restaurantId(entity.getRestaurant().getRestaurantId())
                        .restaurantName(entity.getRestaurant().getRestaurantName())
                        .email(entity.getRestaurant().getEmail())
                        .address(entity.getRestaurant().getAddress())
                        .phone(entity.getRestaurant().getPhone())
                        .build())
                .street(Street.builder()
                        .streetId(entity.getStreet().getStreetId())
                        .name(entity.getStreet().getName())
                        .build())
                .build();
    }

    public RestaurantStreetEntity mapToEntity(RestaurantStreet restaurantStreet) {
        return RestaurantStreetEntity.builder()
                .restaurantStreetId(restaurantStreet.getId())
                .restaurant(RestaurantEntity.builder()
                        .restaurantId(restaurantStreet.getRestaurant().getRestaurantId())
                        .restaurantName(restaurantStreet.getRestaurant().getRestaurantName())
                        .email(restaurantStreet.getRestaurant().getEmail())
                        .address(restaurantStreet.getRestaurant().getAddress())
                        .phone(restaurantStreet.getRestaurant().getPhone())
                        .build())
                .street(StreetEntity.builder()
                        .streetId(restaurantStreet.getStreet().getStreetId())
                        .name(restaurantStreet.getStreet().getName())
                        .build())
                .build();
    }

}
