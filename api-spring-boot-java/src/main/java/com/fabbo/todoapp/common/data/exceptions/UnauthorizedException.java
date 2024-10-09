package com.fabbo.todoapp.common.data.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Action not authorized");
    }
}
