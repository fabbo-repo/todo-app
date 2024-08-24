package com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.responses;

import com.fabbo.todoapp.common.config.TimeZoneConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class TaskRestResponse {
    private UUID id;

    private String title;

    private String description;

    private Boolean isFinished;

    @JsonFormat(pattern = TimeZoneConfig.DATE_TIME_ISO_FORMAT)
    private LocalDateTime deadline;

    @JsonFormat(pattern = TimeZoneConfig.DATE_TIME_ISO_FORMAT)
    private LocalDateTime createdAt;
}
