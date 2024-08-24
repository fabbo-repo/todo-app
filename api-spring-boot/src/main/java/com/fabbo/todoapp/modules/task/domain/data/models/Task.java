package com.fabbo.todoapp.modules.task.domain.data.models;

import com.fabbo.todoapp.common.data.models.AuditableData;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Task {
    private UUID id;

    private String title;

    private String description;

    private boolean isFinished;

    private LocalDateTime deadline;

    private String ownerId;

    private AuditableData auditableData;

    public Task(
            final String title,
            final boolean isFinished,
            final String ownerId
    ) {
        this.title = title;
        this.isFinished = isFinished;
        this.ownerId = ownerId;
    }
}
