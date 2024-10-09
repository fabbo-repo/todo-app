package com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.repositories;

import com.fabbo.todoapp.modules.task.infrastructure.output.repositories.jpa.entities.TaskJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TaskJpaRepository extends JpaRepository<TaskJpaEntity, UUID>,
                                           JpaSpecificationExecutor<TaskJpaEntity> {
}
