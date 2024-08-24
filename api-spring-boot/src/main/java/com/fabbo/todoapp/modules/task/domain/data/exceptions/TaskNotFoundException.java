package com.fabbo.todoapp.modules.task.domain.data.exceptions;

import com.fabbo.todoapp.common.data.exceptions.ApiCodeException;

public class TaskNotFoundException extends ApiCodeException {
    private static final String ERROR_MESSAGE = "Task not found";
    private static final int ERROR_CODE = 100;

    public TaskNotFoundException() {
        super(ERROR_CODE, ERROR_MESSAGE);
    }
}
