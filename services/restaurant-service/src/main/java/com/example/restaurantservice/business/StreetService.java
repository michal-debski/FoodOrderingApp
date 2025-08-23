package com.example.restaurantservice.business;

import com.example.restaurantservice.business.dao.StreetDAO;
import com.example.restaurantservice.domain.Street;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@AllArgsConstructor
public class StreetService {
    private StreetDAO streetDAO;

    public Page<Street> findAllStreets(Pageable pageable) {
        log.info("Trying to find all streets");
        return streetDAO.findAllStreets(pageable);
    }

    public Street findByStreetId(Integer id) {
        log.info("Trying to find street by id{}:",id);
        return streetDAO.findStreetById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Street with id: [%s] is not present in database"
                                .formatted(id)
                        )
                );
    }
}
