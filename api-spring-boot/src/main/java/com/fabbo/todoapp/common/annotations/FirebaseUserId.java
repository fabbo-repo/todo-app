package com.fabbo.todoapp.common.annotations;

import com.fabbo.todoapp.common.annotations.validators.FirebaseUserIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {FirebaseUserIdValidator.class})
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirebaseUserId {
    String message() default "Id has invalid format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
