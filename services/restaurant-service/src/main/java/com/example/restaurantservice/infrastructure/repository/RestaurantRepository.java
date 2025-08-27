package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.business.dao.RestaurantDAO;
import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.exception.NotFoundException;
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
    public Optional<Restaurant> findRestaurantById(String id) {
        return restaurantJpaRepository.findById(Integer.valueOf(id))
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
    public void deleteRestaurant(String restaurantId) {
        Optional<RestaurantEntity> repository = restaurantJpaRepository.findById(Integer.valueOf(restaurantId));
        if (repository.isEmpty()) {
            throw new NotFoundException("Restaurant not found");
        } else {
            restaurantJpaRepository.delete(repository.get());
        }
    }

    @Override
    public List<Restaurant> findAllRestaurantByStreetName(String street) {
        return restaurantJpaRepository.findAllByStreetName(street)
                .stream().map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public List<Restaurant> findRestaurantsByOwnerEmail(String ownerEmail) {
        List<RestaurantEntity> restaurantByOwnerEmail = restaurantJpaRepository.findRestaurantByOwnerEmail(ownerEmail);
        return restaurantByOwnerEmail.stream()
                .map(restaurantEntityMapper::mapFromEntity)
                .toList();
    }


}
