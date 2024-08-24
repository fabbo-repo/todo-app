package com.fabbo.todoapp.modules.task.application.usecases;

import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;

public interface DeleteTaskUseCase {
    Void deleteTask(final GetTaskProps getTaskProps);
}
