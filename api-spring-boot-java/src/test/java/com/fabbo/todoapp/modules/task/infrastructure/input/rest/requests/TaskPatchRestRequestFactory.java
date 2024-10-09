package com.fabbo.todoapp.modules.task.infrastructure.input.rest.requests;

import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPatchRestRequest;

public class TaskPatchRestRequestFactory {
    public static TaskPatchRestRequest taskPatchRestRequest(
            final UpdateTaskProps updateTaskProps
    ) {
        return new TaskPatchRestRequest(
                updateTaskProps.getTitle(),
                updateTaskProps.getDescription(),
                updateTaskProps.getIsFinished(),
                updateTaskProps.getDeadline()
        );
    }
}
