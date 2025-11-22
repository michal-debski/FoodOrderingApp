package com.example.authservice.business.dao;

import com.example.authservice.domain.User;

import java.util.Optional;

public interface UserDAO {

    Optional<User> findByEmail(String email);

    void registerUser(User userToSave);
}
