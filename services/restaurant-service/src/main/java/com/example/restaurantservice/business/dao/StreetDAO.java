package com.example.restaurantservice.business.dao;

import com.example.restaurantservice.domain.Street;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public interface StreetDAO {

    Page<Street> findAllStreets(Pageable pageable);
    Optional<Street> findStreetById(Integer streetId);
}
