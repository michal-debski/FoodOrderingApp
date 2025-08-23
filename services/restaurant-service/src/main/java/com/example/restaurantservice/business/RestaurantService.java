package com.example.restaurantservice.business;

import com.example.restaurantservice.business.dao.RestaurantDAO;
import com.example.restaurantservice.domain.Restaurant;
import com.example.restaurantservice.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantDAO restaurantDAO;


    public List<Restaurant> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantDAO.findAllRestaurants();
        log.info("Available restaurants: [{}]", restaurants.size());
        return restaurants;
    }

    public Restaurant findRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantDAO.findRestaurantById(id);
        if (restaurant.isEmpty()) {
            throw new NotFoundException("Could not find restaurant by name: [%s]".formatted(id));
        }
        return restaurant.get();
    }

    @Transactional
    public Restaurant saveRestaurant(Restaurant restaurant) {
        log.info("Trying to save Restaurant{}:", restaurant);
        return restaurantDAO.saveRestaurant(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Integer restaurantId) {
        log.info("Trying to delete Restaurant{}:", restaurantId);
        restaurantDAO.deleteRestaurant(restaurantId);
    }

    public List<Restaurant> findAllByStreetName(String street) {
        log.info("Trying to find all Restaurants by street{}:", street);
        return restaurantDAO.findAllRestaurantByStreetName(street);
    }
}

