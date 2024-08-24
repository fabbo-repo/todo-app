package com.fabbo.todoapp.common.data.exceptions;

import java.io.IOException;

public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException(final IOException ex) {
        super(ex.getMessage());
    }
}
