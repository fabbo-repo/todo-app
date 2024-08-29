package com.fabbo.todoapp.modules.user.infrastructure.output;

import com.fabbo.todoapp.modules.user.application.repositories.UserRepository;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.mappers.UserJpaMapper;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.repositories.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    private static final UserJpaMapper USER_JPA_MAPPER = UserJpaMapper.INSTANCE;

    @Override
    public boolean existsById(final String id) {
        return userJpaRepository
                .existsById(id);
    }

    @Override
    public Optional<User> findById(final String id) {
        return userJpaRepository
                .findById(id)
                .map(USER_JPA_MAPPER::userEntityToAccount);
    }

    @Override
    public User save(final User user) {
        final UserJpaEntity userJpaEntity = userJpaRepository
                .save(USER_JPA_MAPPER.accountToUserEntity(user));
        return USER_JPA_MAPPER
                .userEntityToAccount(userJpaEntity);
    }
}
