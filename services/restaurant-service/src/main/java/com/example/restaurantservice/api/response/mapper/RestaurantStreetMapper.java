package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.response.RestaurantStreetDTO;
import com.example.restaurantservice.domain.RestaurantStreet;
import org.springframework.stereotype.Component;

@Component
public class RestaurantStreetMapper {

    public RestaurantStreetDTO mapToDTO(RestaurantStreet dto) {
        return RestaurantStreetDTO.builder()
                .id(dto.getId())
                .restaurant(dto.getRestaurant())
                .street(dto.getStreet())
                .build();
    }

    public RestaurantStreet map(RestaurantStreetDTO dto) {
        return RestaurantStreet.builder()
                .restaurant(dto.restaurant())
                .street(dto.street())
                .build();
    }
}
