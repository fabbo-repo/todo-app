package com.fabbo.todoapp.common.annotations.validators;

import com.fabbo.todoapp.common.annotations.FirebaseUserId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FirebaseUserIdValidator implements ConstraintValidator<FirebaseUserId, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !value.isBlank()
               && value.length() < 200;
    }
}
