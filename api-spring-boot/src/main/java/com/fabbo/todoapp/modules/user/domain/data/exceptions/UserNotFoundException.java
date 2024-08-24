package com.fabbo.todoapp.modules.user.domain.data.exceptions;

import com.fabbo.todoapp.common.data.exceptions.ApiCodeException;

public class UserNotFoundException extends ApiCodeException {
    private static final int ERROR_CODE = 20;
    private static final String ERROR_MSG = "User not found";

    public UserNotFoundException() {
        super(ERROR_CODE, ERROR_MSG);
    }
}
