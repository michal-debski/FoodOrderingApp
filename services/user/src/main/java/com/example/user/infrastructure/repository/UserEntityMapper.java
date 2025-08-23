package com.example.user.infrastructure.repository;


import com.example.user.business.User;
import com.example.user.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapper {


    User mapFromEntity(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .phone(userEntity.getPhone())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .surname(userEntity.getSurname())
                .build();
    }


    UserEntity mapToEntity(User user){
        return UserEntity.builder()
                .userId(user.getUserId())
                .phone(user.getPhone())
                .name(user.getName())
                .email(user.getEmail())
                .surname(user.getSurname())
                .build();
    }


}
