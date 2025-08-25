package com.example.mealservice.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "mealId")
@ToString(of = {"mealId", "name","category", "description", "price"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal", schema = "meal_service")
public class MealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "meal_id")
    private String mealId;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "restaurantId")
    private String restaurantId;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MealIngredientEntity> ingredients;
}
