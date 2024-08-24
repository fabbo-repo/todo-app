package com.fabbo.todoapp.modules.task.domain.services;

import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.application.usecases.CreateTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.GetTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.GetTasksUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.fabbo.todoapp.modules.task.domain.data.exceptions.TaskNotFoundException;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;
import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService implements GetTaskUseCase, DeleteTaskUseCase,
                                    GetTasksUseCase, CreateTaskUseCase,
                                    UpdateTaskUseCase {

    private final TaskRepository taskRepository;

    private final GetUserUseCase getUserUseCase;

    @Override
    public Task getTask(final GetTaskProps getTaskProps) {
        getUserUseCase.getUser(getTaskProps.getGetUserProps());
        return taskRepository
                .findById(
                        getTaskProps.getTaskId()
                )
                .orElseThrow(TaskNotFoundException::new);
    }

    @Override
    public Void deleteTask(final GetTaskProps getTaskProps) {
        getUserUseCase.getUser(getTaskProps.getGetUserProps());
        taskRepository.deleteById(
                getTaskProps.getTaskId()
        );
        return null;
    }

    @Override
    public ApiPage<Task> getTasks(final GetTasksProps getTasksProps) {
        getUserUseCase.getUser(getTasksProps.getGetUserProps());
        return taskRepository
                .findAll(
                        getTasksProps.getFilter(),
                        getTasksProps
                );
    }

    @Override
    public Task createTask(final CreateTaskProps createTaskProps) {
        final User user = getUserUseCase.getUser(createTaskProps.getGetUserProps());
        final Task task = new Task(
                createTaskProps.getTitle(),
                true,
                user.getId()
        );
        task.setDescription(createTaskProps.getDescription());
        task.setDeadline(createTaskProps.getDeadline());
        return taskRepository.save(task);
    }

    @Override
    public Void updateTask(final UpdateTaskProps updateTaskProps) {
        final Task task = getTask(updateTaskProps);

        if (updateTaskProps.getTitle() != null) {
            task.setTitle(
                    updateTaskProps.getTitle()
            );
        }
        if (updateTaskProps.getDescription() != null) {
            task.setDescription(
                    updateTaskProps.getDescription()
            );
        }
        if (updateTaskProps.getIsFinished() != null) {
            task.setFinished(
                    updateTaskProps.getIsFinished()
            );
        }
        if (updateTaskProps.getDeadline() != null) {
            task.setDeadline(
                    updateTaskProps.getDeadline()
            );
        }
        taskRepository.save(task);
        return null;
    }
}
