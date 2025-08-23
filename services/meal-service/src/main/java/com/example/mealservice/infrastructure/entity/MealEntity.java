package com.example.mealservice.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "mealId")
@ToString(of = {"mealId", "name","category", "description", "price"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "meal")
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

}
