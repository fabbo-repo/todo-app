package com.fabbo.todoapp.modules.user.application.repositories;

import com.fabbo.todoapp.modules.user.domain.data.models.User;

import java.util.Optional;

public interface UserRepository {
    boolean existsById(String id);

    Optional<User> findById(String id);

    User save(User user);
}
