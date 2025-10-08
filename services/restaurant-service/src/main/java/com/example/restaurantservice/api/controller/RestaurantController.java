package com.example.restaurantservice.api.controller;

import com.example.restaurantservice.api.request.RestaurantRequest;
import com.example.restaurantservice.api.response.RestaurantDTO;
import com.example.restaurantservice.api.response.mapper.RestaurantMapper;
import com.example.restaurantservice.business.RestaurantService;
import com.example.restaurantservice.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;


    @PostMapping("/addRestaurant")
    public ResponseEntity<?> makeRestaurant(
            @RequestBody RestaurantRequest request,
            @RequestHeader("X-User-Email") String ownerEmail
    ) {
        Restaurant restaurant = restaurantMapper.mapFromDto(request, ownerEmail);
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        RestaurantDTO savedRestaurantInDTO = restaurantMapper.map(savedRestaurant);
        return new ResponseEntity<>(savedRestaurantInDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> deleteRestaurant(
            @PathVariable String restaurantId
    ) {
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsByOwnerEmail(
            @RequestHeader("X-User-Email") String ownerEmail
    ) {
        log.info("Received X-User-Email: {}", ownerEmail);
        List<Restaurant> allRestaurantsByOwnerEmail = restaurantService.findAllRestaurantsByOwnerEmail(ownerEmail);
        List<RestaurantDTO> restaurantDTOList = allRestaurantsByOwnerEmail.stream()
                .map(restaurantMapper::map)
                .toList();
        log.info("Found {} restaurants, list below: \n{}", restaurantDTOList.size(), restaurantDTOList);
        return new ResponseEntity<>(restaurantDTOList, HttpStatus.OK);
    }

    @GetMapping("/allRestaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        List<Restaurant> allRestaurantsByOwnerEmail = restaurantService.findAllRestaurants();
        List<RestaurantDTO> restaurantDTOList = allRestaurantsByOwnerEmail.stream()
                .map(restaurantMapper::map)
                .toList();
        log.info("Found {} restaurants, list below: \n{}", restaurantDTOList.size(), restaurantDTOList);
        return new ResponseEntity<>(restaurantDTOList, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurantsByStreetName(
            @RequestParam String streetName
    ) {
        List<Restaurant> allRestaurantsByStreetName = restaurantService.findAllByStreetName(streetName);

        List<RestaurantDTO> restaurantDtoByStreetName = allRestaurantsByStreetName.stream()
                .map(restaurantMapper::map)
                .toList();

        return new ResponseEntity<>(restaurantDtoByStreetName, HttpStatus.OK);
    }


}


