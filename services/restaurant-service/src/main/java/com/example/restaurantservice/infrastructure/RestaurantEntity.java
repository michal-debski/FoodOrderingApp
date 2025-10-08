package com.example.restaurantservice.infrastructure;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "restaurantId")
@ToString(of = {"restaurantId", "restaurantName", "email", "phone", "ownerEmail"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurant", schema = "restaurant_service")
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "name")
    private String restaurantName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "owner_email")
    private String ownerEmail;

    @ManyToMany
    @JoinTable(
            name = "restaurant_street",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "street_id")
    )
    private Set<StreetEntity> streets = new HashSet<>();

}
