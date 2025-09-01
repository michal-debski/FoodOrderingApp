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
                .restaurantId(entity.getRestaurant().getRestaurantId())
                .street(Street.builder()
                        .streetId(entity.getStreet().getStreetId())
                        .name(entity.getStreet().getName())
                        .build())
                .build();
    }

    public RestaurantStreetEntity mapToEntity(RestaurantStreet restaurantStreet, Restaurant restaurant) {
        return RestaurantStreetEntity.builder()
                .restaurantStreetId(restaurantStreet.getId())
                .restaurant(RestaurantEntity.builder()
                        .restaurantId(restaurant.getRestaurantId())
                        .restaurantName(restaurant.getRestaurantName())
                        .email(restaurant.getEmail())
                        .address(restaurant.getAddress())
                        .phone(restaurant.getPhone())
                        .build())
                .street(StreetEntity.builder()
                        .streetId(restaurantStreet.getStreet().getStreetId())
                        .name(restaurantStreet.getStreet().getName())
                        .build())
                .build();
    }

}
