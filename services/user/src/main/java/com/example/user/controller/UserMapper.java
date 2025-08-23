package com.example.user.controller;


import com.example.user.business.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


     UserDTO mapToDTO(User user){
          return UserDTO.builder()
                  .userId(user.getUserId())
                  .name(user.getName())
                  .surname(user.getSurname())
                  .email(user.getEmail())
                  .phone(user.getPhone())
                  .build();
     }

     User map(UserDTO userDTO){
          return User.builder()
                  .userId(userDTO.userId())
                  .name(userDTO.name())
                  .surname(userDTO.surname())
                  .email(userDTO.email())
                  .phone(userDTO.phone())
                  .build();
     }
}
