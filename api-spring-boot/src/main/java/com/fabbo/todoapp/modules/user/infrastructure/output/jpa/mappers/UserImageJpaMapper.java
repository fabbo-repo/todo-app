package com.fabbo.todoapp.modules.user.infrastructure.output.jpa.mappers;

import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserImageJpaEntity;
import com.fabbo.todoapp.modules.user.infrastructure.output.jpa.entities.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserImageJpaMapper {
    UserImageJpaMapper INSTANCE = Mappers.getMapper(UserImageJpaMapper.class);

    ApiImage userImageEntityToApiImage(final UserImageJpaEntity userImageJpaEntity);

    @Mapping(source = "apiImage.id", target = "id")
    @Mapping(source = "apiImage.createdAt", target = "createdAt")
    @Mapping(source = "apiImage.url", target = "url")
    @Mapping(source = "apiImage.urlExpiration", target = "urlExpiration")
    @Mapping(source = "user", target = "user")
    UserImageJpaEntity apiImageToUserImageEntity(
            final ApiImage apiImage,
            final UserJpaEntity user
    );
}
