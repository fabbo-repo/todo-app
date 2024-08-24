package com.fabbo.todoapp.modules.task.application.usecases;

import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;

public interface CreateTaskUseCase {
    Task createTask(final CreateTaskProps createTaskProps);
}
