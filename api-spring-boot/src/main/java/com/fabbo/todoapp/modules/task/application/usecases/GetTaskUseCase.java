package com.fabbo.todoapp.modules.task.application.usecases;

import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;

public interface GetTaskUseCase {
    Task getTask(final GetTaskProps getTaskProps);
}
