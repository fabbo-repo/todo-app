package com.fabbo.todoapp.modules.task.infrasructure.output.persistence.jpa.entities;

import com.fabbo.todoapp.modules.task.infrastructure.output.persistence.jpa.entities.TaskJpaEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomFutureDateTime;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomNullableText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomUuid;

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
