package com.example.mealservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@EqualsAndHashCode(of = "ingredientId")
@ToString(of = {"ingredientId", "name","category", "quantity", "unitName"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "storage", schema = "meal_service")
public class IngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ingredient_id")
    private String ingredientId;

    @Column(name = "ingredient_name")
    private String name;

    private int quantity;

    @Column(name = "unit")
    private String unitName;

    private String restaurantId;
}
