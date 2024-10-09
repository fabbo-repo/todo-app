package com.fabbo.todoapp.modules.task.infrastructure.input.rest.requests;

import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPostRestRequest;

public class TaskPostRestRequestFactory {
    public static TaskPostRestRequest taskPostRestRequest(
            final CreateTaskProps createTaskProps
    ) {
        return new TaskPostRestRequest(
                createTaskProps.getTitle(),
                createTaskProps.getDescription(),
                createTaskProps.getDeadline()
        );
    }
}
