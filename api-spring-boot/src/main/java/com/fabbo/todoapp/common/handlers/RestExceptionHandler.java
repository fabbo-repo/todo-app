package com.fabbo.todoapp.common.handlers;

import com.fabbo.todoapp.common.data.exceptions.ApiCodeException;
import com.fabbo.todoapp.common.data.exceptions.ApiPageNotFoundException;
import com.fabbo.todoapp.common.data.exceptions.UnauthorizedException;
import com.fabbo.todoapp.common.data.models.ApiError;
import com.fabbo.todoapp.common.data.models.ApiParamError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(final Exception ex) {
        logger.error(ex.getClass() + ": " + ex.getMessage());
        logger.error(ExceptionUtils.getStackTrace(ex));

        final ApiError errorResponse = ApiError
                .builder()
                .message("Internal Server Error")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Will get invoke when invalid data is sent in rest request object.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex, @NonNull final HttpHeaders headers,
            @NonNull final HttpStatusCode status,
            @NonNull final WebRequest request
    ) {
        final List<ApiParamError> paramErrors = new ArrayList<>();
        for (final FieldError error : ex.getFieldErrors()) {
            paramErrors.add(
                    ApiParamError
                            .builder()
                            .field(error.getField())
                            .message(error.getDefaultMessage())
                            .build()
            );
        }
        final ApiError response = ApiError
                .builder()
                .fields(paramErrors)
                .message("Param errors")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(
            final ConstraintViolationException ex
    ) {
        final List<ApiParamError> paramErrors = new ArrayList<>();
        for (final ConstraintViolation<?> error : ex.getConstraintViolations()) {
            paramErrors.add(
                    ApiParamError
                            .builder()
                            .field(error.getPropertyPath().toString().split("\\.")[1])
                            .message(error.getMessage())
                            .build()
            );
        }
        final ApiError response = ApiError
                .builder()
                .fields(paramErrors)
                .message("Param errors")
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Will get invoke when invalid path variable is sent in rest request.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(
            final MethodArgumentTypeMismatchException ex
    ) {
        final ApiError errorResponse = ApiError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
            {
                    AccessDeniedException.class,
                    UnauthorizedException.class
            }
    )
    public ResponseEntity<ApiError> handleUnauthorizedException(final RuntimeException ex) {
        logger.warn(ex.getMessage());
        final ApiError errorResponse = ApiError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApiPageNotFoundException.class)
    public ResponseEntity<ApiError> handlePageNotFoundException(final ApiPageNotFoundException ex) {
        final ApiError errorResponse = ApiError
                .builder()
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiCodeException.class)
    public ResponseEntity<ApiError> handleApiCodeException(final ApiCodeException ex) {
        final ApiError errorResponse = ApiError
                .builder()
                .errorCode(ex.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
