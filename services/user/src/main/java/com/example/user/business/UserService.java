package com.example.user.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserDAO userDAO;


    public User findUserById(Integer userId) {
        log.info("Trying to find User: {}", userId);
        return userDAO.findUserById(userId);
    }

    @Transactional
    public User saveUser(User user) {
        log.info("Trying to save User: {}", user);
        return userDAO.saveUser(user);
    }
}
