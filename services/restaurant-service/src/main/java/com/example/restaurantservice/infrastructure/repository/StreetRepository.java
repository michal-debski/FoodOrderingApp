package com.example.restaurantservice.infrastructure.repository;

import com.example.restaurantservice.business.dao.StreetDAO;
import com.example.restaurantservice.domain.Street;
import com.example.restaurantservice.infrastructure.repository.mapper.StreetEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
@AllArgsConstructor
public class StreetRepository implements StreetDAO {

    private final StreetJpaRepository streetJpaRepository;
    private final StreetEntityMapper streetEntityMapper;

    @Override
    public Page<Street> findAllStreets(Pageable pageable) {
        return streetJpaRepository.findAll(pageable)
                .map(streetEntityMapper::mapFromEntity);

    }

    @Override
    public Optional<Street> findStreetById(Integer streetId) {
        return streetJpaRepository.findById(streetId)
                .map(streetEntityMapper::mapFromEntity);
    }
}
