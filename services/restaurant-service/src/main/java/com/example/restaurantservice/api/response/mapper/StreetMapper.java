package com.example.restaurantservice.api.response.mapper;


import com.example.restaurantservice.api.response.StreetDTO;
import com.example.restaurantservice.domain.Street;
import org.springframework.stereotype.Component;


@Component
public class StreetMapper {

    StreetDTO map(Street street){
        return StreetDTO.builder()
                .name(street.getName())
                .streetId(street.getStreetId())
                .build();
    }

    Street mapFromDTO(StreetDTO dto) {
        return Street.builder()
                .name(dto.name())
                .streetId(dto.streetId())
                .build();
    }
}
