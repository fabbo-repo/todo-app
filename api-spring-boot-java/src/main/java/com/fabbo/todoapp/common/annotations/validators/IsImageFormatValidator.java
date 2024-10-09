package com.fabbo.todoapp.common.annotations.validators;

import com.fabbo.todoapp.common.annotations.IsImageFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public class IsImageFormatValidator implements ConstraintValidator<IsImageFormat, MultipartFile> {
    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        final String fileContentType = value.getContentType();

        return value.getContentType() != null
               && ("image/webp".equalsIgnoreCase(fileContentType)
                   || MediaType.IMAGE_JPEG_VALUE.equalsIgnoreCase(fileContentType)
                   || MediaType.IMAGE_PNG_VALUE.equalsIgnoreCase(fileContentType));
    }
}
