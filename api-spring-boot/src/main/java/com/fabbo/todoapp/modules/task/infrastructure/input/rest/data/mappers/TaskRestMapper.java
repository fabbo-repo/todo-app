package com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.mappers;

import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.responses.TaskRestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskRestMapper {
    TaskRestMapper INSTANCE = Mappers.getMapper(TaskRestMapper.class);

    @Mapping(source = "finished", target = "isFinished")
    @Mapping(target = "createdAt", source = "auditableData.createdAt")
    TaskRestResponse taskToResponse(final Task task);
}
