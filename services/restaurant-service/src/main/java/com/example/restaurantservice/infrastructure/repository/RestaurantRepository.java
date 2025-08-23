package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.business.dao.RestaurantDAO;
import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.infrastructure.RestaurantEntity;
import com.example.restaurantservice.infrastructure.repository.mapper.RestaurantEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantRepository implements RestaurantDAO {

    private final RestaurantJpaRepository restaurantJpaRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;


    @Override
    public Optional<Restaurant> findRestaurantById(Integer id) {
        return restaurantJpaRepository.findById(id)
                .map(restaurantEntityMapper::mapFromEntity);
    }

    @Override
    public List<Restaurant> findAllRestaurants() {
        return restaurantJpaRepository.findAll().stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantEntityMapper.mapToEntity(restaurant);
        RestaurantEntity restaurantSaved = restaurantJpaRepository.save(restaurantEntity);
        return restaurantEntityMapper.mapFromEntity(restaurantSaved);
    }

    @Override
    public void deleteRestaurant(Integer restaurantId) {
        restaurantJpaRepository.delete(restaurantJpaRepository.findById(restaurantId).get());
    }

    @Override
    public List<Restaurant> findAllRestaurantByStreetName(String street) {
        return restaurantJpaRepository.findAllByStreetName(street)
                .stream().map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }


}
