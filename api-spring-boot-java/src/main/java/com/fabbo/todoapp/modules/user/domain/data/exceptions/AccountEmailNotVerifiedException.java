package com.fabbo.todoapp.modules.user.domain.data.exceptions;

import com.fabbo.todoapp.common.data.exceptions.ApiCodeException;

public class AccountEmailNotVerifiedException extends ApiCodeException {
    private static final int ERROR_CODE = 1;
    private static final String ERROR_MSG = "Account email not verified";

    public AccountEmailNotVerifiedException() {
        super(ERROR_CODE, ERROR_MSG);
    }
}
