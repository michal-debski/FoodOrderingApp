package com.example.user.business;

import lombok.*;

@With
@Value
@Builder
@EqualsAndHashCode(of = "userId")
@ToString(of = {"userId", "name", "surname", "phone", "email"})
public class User {

    Integer userId;
    String name;
    String surname;
    String email;
    String phone;

}
