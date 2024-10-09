package com.fabbo.todoapp.modules.user.infrastructure.output.repositories;

import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserImageJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.mappers.UserImageJpaMapper;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.repositories.UserImageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserImageRepositoryImpl implements UserImageRepository {

    private final UserImageJpaRepository userImageJpaRepository;

    private static final UserImageJpaMapper USER_IMAGE_JPA_MAPPER = UserImageJpaMapper.INSTANCE;

    @Override
    public boolean existsById(final UUID id) {
        return userImageJpaRepository
                .existsById(id);
    }

    @Override
    public Optional<ApiImage> findByUserId(
            final String userId
    ) {
        return userImageJpaRepository
                .findByUserId(userId)
                .map(USER_IMAGE_JPA_MAPPER::userImageEntityToApiImage);
    }

    @Override
    public ApiImage save(final ApiImage apiImage, final String userId) {
        final UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(userId);
        final UserImageJpaEntity userImageJpaEntity = userImageJpaRepository
                .save(
                        USER_IMAGE_JPA_MAPPER.apiImageToUserImageEntity(
                                apiImage,
                                userJpaEntity
                        )
                );
        return USER_IMAGE_JPA_MAPPER.userImageEntityToApiImage(userImageJpaEntity);
    }
}
