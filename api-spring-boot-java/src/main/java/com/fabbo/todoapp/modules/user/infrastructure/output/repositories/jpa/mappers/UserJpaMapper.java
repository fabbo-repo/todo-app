package com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.mappers;

import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.infrastructure.output.repositories.jpa.entities.UserJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserJpaMapper {
    UserJpaMapper INSTANCE = Mappers.getMapper(UserJpaMapper.class);

    @Mapping(target = "image", ignore = true)
    @Mapping(source = "createdAt", target = "auditableData.createdAt")
    @Mapping(source = "createdBy", target = "auditableData.createdBy")
    @Mapping(source = "updatedAt", target = "auditableData.updatedAt")
    @Mapping(source = "updatedBy", target = "auditableData.updatedBy")
    User userEntityToAccount(final UserJpaEntity userJpaEntity);

    @Mapping(source = "auditableData.createdAt", target = "createdAt")
    @Mapping(source = "auditableData.createdBy", target = "createdBy")
    @Mapping(source = "auditableData.updatedAt", target = "updatedAt")
    @Mapping(source = "auditableData.updatedBy", target = "updatedBy")
    UserJpaEntity accountToUserEntity(final User user);
}
