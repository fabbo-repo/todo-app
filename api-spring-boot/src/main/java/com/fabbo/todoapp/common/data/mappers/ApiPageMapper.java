package com.fabbo.todoapp.common.data.mappers;

import com.fabbo.todoapp.common.data.exceptions.ApiPageNotFoundException;
import com.fabbo.todoapp.common.data.models.ApiPage;
import org.springframework.data.domain.Page;

public class ApiPageMapper<T> {

    public ApiPage<T> entityToDto(final Page<T> page) {
        if (page.getTotalPages() < page.getNumber()) {
            throw new ApiPageNotFoundException(
                    page.getNumber()
            );
        }
        return new ApiPage<>(
                page.getTotalElements(),
                page.getNumberOfElements(),
                page.getNumber(),
                0,
                Math.max(0, page.getTotalPages() - 1L),
                page.getContent()
        );
    }
}
