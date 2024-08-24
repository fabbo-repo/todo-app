package com.fabbo.todoapp.common.annotations;

import com.fabbo.todoapp.common.annotations.validators.MaxSizeFileValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {MaxSizeFileValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSizeFile {
    String message() default "Request file is too large";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    // File size in KB
    int size() default 700;
}