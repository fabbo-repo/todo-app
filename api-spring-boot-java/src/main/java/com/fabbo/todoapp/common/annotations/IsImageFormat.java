package com.fabbo.todoapp.common.annotations;

import com.fabbo.todoapp.common.annotations.validators.IsImageFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {IsImageFormatValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsImageFormat {
    String message() default "file format not accepted";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
