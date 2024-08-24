package com.fabbo.todoapp.modules.task.application.usecases;

import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;

public interface GetTasksUseCase {
    ApiPage<Task> getTasks(final GetTasksProps getTasksProps);
}
