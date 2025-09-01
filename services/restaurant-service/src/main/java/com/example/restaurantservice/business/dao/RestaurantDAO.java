package com.example.restaurantservice.business.dao;



import com.example.restaurantservice.domain.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RestaurantDAO {


    Optional<Restaurant> findRestaurantById(String id);

    List<Restaurant> findAllRestaurants();

    Restaurant saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(String restaurantId);

    List<Restaurant> findAllRestaurantByStreetName(String street);

    List<Restaurant> findRestaurantsByOwnerEmail(String ownerEmail);
}
