package com.example.restaurantservice.business.dao;



import com.example.restaurantservice.domain.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface RestaurantDAO {


    Optional<Restaurant> findRestaurantById(Integer id);

    List<Restaurant> findAllRestaurants();

    Restaurant saveRestaurant(Restaurant restaurant);

    void deleteRestaurant(Integer restaurantId);


    List<Restaurant> findAllRestaurantByStreetName(String street);
}
