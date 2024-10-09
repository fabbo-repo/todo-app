package com.fabbo.todoapp.modules.task.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.exceptions.TaskNotFoundException;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.UpdateTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class UpdateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserUseCase getUserUseCase;

    @InjectMocks
    private TaskService updateTaskUseCase;

    private static final ArgumentCaptor<Task> TASK_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(Task.class);

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void givenValidProps_whenUpdateTask_shouldSaveValidTask() {
        final UpdateTaskProps updateTaskProps = UpdateTaskPropsFactory
                .updateTaskProps(userId);

        final Task storedTask = new Task(
                "Test",
                true,
                userId
        );

        final Task expectedTask = new Task(
                updateTaskProps.getTitle(),
                true,
                userId
        );
        expectedTask.setDescription(updateTaskProps.getDescription());
        expectedTask.setFinished(updateTaskProps.getIsFinished());
        expectedTask.setDeadline(updateTaskProps.getDeadline());

        when(
                taskRepository
                        .findById(updateTaskProps.getTaskId())
        ).thenReturn(
                Optional.of(storedTask)
        );

        updateTaskUseCase.updateTask(updateTaskProps);

        verify(taskRepository)
                .save(
                        TASK_ARGUMENT_CAPTOR.capture()
                );

        assertEquals(expectedTask, TASK_ARGUMENT_CAPTOR.getValue());
    }

    @Test
    void storedNoTask_whenUpdateTask_shouldThrowException() {
        final UpdateTaskProps updateTaskProps = UpdateTaskPropsFactory
                .updateTaskProps(userId);

        when(
                taskRepository
                        .findById(
                                updateTaskProps.getTaskId()
                        )
        ).thenThrow(TaskNotFoundException.class);

        assertThrows(
                TaskNotFoundException.class,
                () ->
                        updateTaskUseCase
                                .updateTask(updateTaskProps)
        );
    }
}
