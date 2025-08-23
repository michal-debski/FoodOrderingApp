package com.example.user.business;

import org.springframework.stereotype.Component;

@Component
public interface UserDAO {
    User findUserById(Integer id);

    User saveUser(User user);

}
