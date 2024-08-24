package com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.mappers;

import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.entities.TaskJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskJpaMapper {
    TaskJpaMapper INSTANCE = Mappers.getMapper(TaskJpaMapper.class);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "finished", target = "isFinished")
    @Mapping(target = "auditableData.createdAt", source = "createdAt")
    @Mapping(target = "auditableData.createdBy", source = "createdBy")
    @Mapping(target = "auditableData.updatedAt", source = "updatedAt")
    @Mapping(target = "auditableData.updatedBy", source = "updatedBy")
    Task entityToTask(final TaskJpaEntity taskJpaEntity);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(target = "createdAt", source = "auditableData.createdAt")
    @Mapping(target = "createdBy", source = "auditableData.createdBy")
    @Mapping(target = "updatedAt", source = "auditableData.updatedAt")
    @Mapping(target = "updatedBy", source = "auditableData.updatedBy")
    TaskJpaEntity taskToEntity(final Task task);
}
