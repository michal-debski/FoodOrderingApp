package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.business.dao.RestaurantStreetDAO;
import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.domain.RestaurantStreet;
import com.example.restaurantservice.infrastructure.repository.mapper.RestaurantStreetEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class RestaurantStreetRepository implements RestaurantStreetDAO {

    private final RestaurantStreetJpaRepository restaurantStreetJpaRepository;
    private final RestaurantStreetEntityMapper restaurantStreetEntityMapper;
    @Override
    public List<RestaurantStreet> findAllRestaurantStreets() {
        return restaurantStreetJpaRepository.findAll().stream()
                .map(restaurantStreetEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public RestaurantStreet saveRestaurantStreet(RestaurantStreet restaurantStreet, Restaurant restaurant) {
        return restaurantStreetEntityMapper.mapFromEntity(restaurantStreetJpaRepository
                .save(restaurantStreetEntityMapper.mapToEntity(restaurantStreet, restaurant)));
    }

}
