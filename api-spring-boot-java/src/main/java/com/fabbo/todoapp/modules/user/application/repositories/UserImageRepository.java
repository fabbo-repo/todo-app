package com.fabbo.todoapp.modules.user.application.repositories;

import com.fabbo.todoapp.common.data.models.ApiImage;

import java.util.Optional;

public interface UserImageRepository {
    Optional<ApiImage> findByUserId(String userId);

    ApiImage save(ApiImage apiImage, String userId);

    void deleteByUserId(String userId);
}
