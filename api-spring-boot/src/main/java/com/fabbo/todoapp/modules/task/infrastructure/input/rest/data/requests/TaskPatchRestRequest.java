package com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests;

import com.fabbo.todoapp.common.config.TimeZoneConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TaskPatchRestRequest {
    @Size(min = 1, max = 50)
    private String title;

    @Size(max = 300)
    private String description;

    private Boolean isFinished;

    @JsonFormat(pattern = TimeZoneConfig.DATE_TIME_ISO_FORMAT)
    private LocalDateTime deadline;
}
