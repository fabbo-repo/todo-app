package com.fabbo.todoapp.common.annotations.validators;

import com.fabbo.todoapp.common.annotations.MaxSizeFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MaxSizeFileValidator implements ConstraintValidator<MaxSizeFile, MultipartFile> {
    // Max size in KB
    private int maxSize;

    @Override
    public void initialize(MaxSizeFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.size();
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return value.getSize() < maxSize * 1024L;
    }
}
