package com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fabbo.todoapp.common.utils.TestDataUtils.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskJpaEntityFactory {
    public static TaskJpaEntity taskJpaEntity() {
        return new TaskJpaEntity(
                randomUuid(),
                randomText(50),
                randomNullableText(300),
                randomBool(),
                randomFutureDateTime(),
                null
        );
    }
}
