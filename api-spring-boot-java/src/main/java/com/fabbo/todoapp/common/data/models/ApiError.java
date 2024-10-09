package com.fabbo.todoapp.common.data.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        Integer errorCode,
        String message,
        List<ApiParamError> fields
) {
}
