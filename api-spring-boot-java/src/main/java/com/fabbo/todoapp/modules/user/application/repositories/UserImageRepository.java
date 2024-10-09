package com.fabbo.todoapp.modules.user.application.repositories;

import com.fabbo.todoapp.common.data.models.ApiImage;

import java.util.Optional;
import java.util.UUID;

public interface UserImageRepository {
    boolean existsById(UUID id);

    Optional<ApiImage> findByUserId(String userId);

    ApiImage save(ApiImage apiImage, String userId);
}
