package com.fabbo.todoapp.modules.task.application.usecases;

import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;

public interface UpdateTaskUseCase {
    Void updateTask(final UpdateTaskProps updateTaskProps);
}
