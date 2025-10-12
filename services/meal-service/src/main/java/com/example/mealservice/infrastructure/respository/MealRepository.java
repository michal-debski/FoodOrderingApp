package com.example.mealservice.infrastructure.respository;

import com.example.mealservice.business.MealDAO;
import com.example.mealservice.domain.Meal;
import com.example.mealservice.infrastructure.entity.MealEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@AllArgsConstructor
public class MealRepository implements MealDAO {


    private final MealJpaRepository mealJpaRepository;
    private final MealEntityMapper mealEntityMapper;


    @Override
    public Set<Meal> findAllMealsByCategory(String category) {
        return mealJpaRepository.findByCategory(category).stream()
                .map(mealEntityMapper::mapFromEntity).collect(Collectors.toSet());
    }


    @Override
    public List<Meal> findAllMeals() {
        return mealJpaRepository.findAll().stream()
                .map(mealEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Meal saveMeal(Meal meal) {
        MealEntity mealEntity = mealEntityMapper.mapToEntity(meal);
        MealEntity entitySaved = mealJpaRepository.save(mealEntity);
        log.info("Saved: {} ", entitySaved);
        return mealEntityMapper.mapFromEntity(entitySaved);
    }

    @Override
    public void deleteById(String id) {
        mealJpaRepository.delete(mealJpaRepository.findById(id).get());
    }

    @Override
    public List<Meal> findAllMealsBySelectedRestaurant(String id) {
        return mealJpaRepository.findAllByRestaurantId(id)
                .stream().map(mealEntityMapper::mapFromEntity).toList();
    }

    @Override
    public Optional<Meal> findMealById(String mealId) {
        return mealJpaRepository.findById(mealId)
                .map(mealEntityMapper::mapFromEntity);
    }

    @Override
    public Meal updateMeal(Meal meal) {
        return mealEntityMapper.mapFromEntity(
                mealJpaRepository.save(
                        mealEntityMapper.mapToEntity(meal)
                )
        );
    }

}
