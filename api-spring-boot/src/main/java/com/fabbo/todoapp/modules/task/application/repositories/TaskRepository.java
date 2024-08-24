package com.fabbo.todoapp.modules.task.application.repositories;

import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.common.data.props.ApiPageProps;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.FilterTasksProps;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository {
    Optional<Task> findById(UUID id);

    ApiPage<Task> findAll(
            FilterTasksProps filter,
            ApiPageProps pageProps
    );

    boolean existsById(UUID id);

    Task save(Task task);

    void deleteById(UUID id);
}
