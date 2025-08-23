package com.example.user.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@Table(name = "new_user")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userId;

        private String name;

        private String surname;

        private String email;

        private String phone;

}
