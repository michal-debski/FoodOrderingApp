package com.example.restaurantservice.infrastructure.repository.mapper;


import com.example.restaurantservice.domain.Street;
import com.example.restaurantservice.infrastructure.StreetEntity;
import org.springframework.stereotype.Component;

@Component
public class StreetEntityMapper {
    public Street mapFromEntity(StreetEntity entity){
        return Street.builder()
                .streetId(entity.getStreetId())
                .name(entity.getName())
                .build();
    }

    StreetEntity mapToEntity(Street street){
        return StreetEntity.builder()
                .streetId(street.getStreetId())
                .name(street.getName())
                .build();
    }

}
