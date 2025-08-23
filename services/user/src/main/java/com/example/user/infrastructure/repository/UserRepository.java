package com.example.user.infrastructure.repository;

import com.example.user.business.User;
import com.example.user.business.UserDAO;
import com.example.user.infrastructure.NotFoundException;
import com.example.user.infrastructure.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserRepository implements UserDAO {

    private final UserJpaRepository userJpaRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public User findUserById(Integer id) {
        return userJpaRepository.findById(id)
                .map(userEntityMapper::mapFromEntity).orElseThrow(
                        () -> new NotFoundException("Restaurant Owner not found")
                );
    }

    @Override
    public User saveUser(User user) {
        UserEntity toSave = userEntityMapper.mapToEntity(user);
        UserEntity saved = userJpaRepository.save(toSave);
        log.info("Restaurant Owner was created successfully with email: " + saved.getEmail());
        return userEntityMapper.mapFromEntity(saved);
    }

}
