package com.example.authservice.infrastructure.repository;

import com.example.authservice.business.dao.UserDAO;
import com.example.authservice.domain.User;
import com.example.authservice.infrastructure.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userEntityMapper::mapToUser);
    }

    @Override
    public void registerUser(User userToSave) {
        UserEntity entity = userEntityMapper.mapToEntity(userToSave);
        userJpaRepository.save(entity);
    }
}
