package com.example.restaurantservice.infrastructure;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "streetId")
@ToString(of = {"streetId", "name"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "street")
public class StreetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_id")
    private Integer streetId;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "street")
    @Fetch(value= FetchMode.JOIN)
    private Set<RestaurantStreetEntity> restaurantStreets;
}
