package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.response.RestaurantStreetDTO;
import com.example.restaurantservice.api.response.StreetDTO;
import com.example.restaurantservice.domain.RestaurantStreet;
import com.example.restaurantservice.domain.Street;
import org.springframework.stereotype.Component;

@Component
public class RestaurantStreetMapper {

    public RestaurantStreetDTO mapToDTO(RestaurantStreet restaurantStreet) {
        return RestaurantStreetDTO.builder()
                .id(restaurantStreet.getId())
                .restaurantId(restaurantStreet.getRestaurantId())
                .street(StreetDTO.builder()
                        .name(restaurantStreet.getStreet().getName())
                        .build())
                .build();
    }

    public RestaurantStreet map(RestaurantStreetDTO dto) {
        return RestaurantStreet.builder()
                .restaurantId(dto.restaurantId())
                .street(Street.builder()
                        .streetId(dto.street().streetId())
                        .name(dto.street().name())
                        .build())
                .build();
    }
}
