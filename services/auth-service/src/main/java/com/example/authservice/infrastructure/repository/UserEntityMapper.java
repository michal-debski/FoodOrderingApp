package com.example.authservice.infrastructure.repository;

import com.example.authservice.domain.User;
import com.example.authservice.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {
    public User mapToUser(UserEntity userEntity) {
        return new User(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole()
        );
    }

    public UserEntity mapToEntity(User user) {
        return UserEntity.builder()
                .email(user.email())
                .role(user.role())
                .password(user.password())
                .build();
    }

}
