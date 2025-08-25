package com.example.mealservice.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "ingredientId")
@ToString(of = {"ingredientId", "name","quantity", "meal"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ingredients", schema = "meal_service")
public class MealIngredientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ingredient_id")
    private String ingredientId;

    @Column(name = "name")
    private String name;

    @Column(name = "quantity")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    private MealEntity meal;

}
