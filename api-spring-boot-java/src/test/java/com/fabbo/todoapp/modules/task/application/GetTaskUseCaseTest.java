package com.fabbo.todoapp.modules.task.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.exceptions.TaskNotFoundException;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.GetTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class GetTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserUseCase getUserUseCase;

    @InjectMocks
    private TaskService getTaskUseCase;

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void storedTask_whenGetTask_shouldReturnValidTask() {
        final GetTaskProps getTaskProps = GetTaskPropsFactory
                .getTaskProps(userId);

        final Task expectedTask = new Task(
                randomText(10),
                randomBool(),
                userId
        );

        when(
                taskRepository
                        .findById(
                                getTaskProps.getTaskId()
                        )
        ).thenReturn(Optional.of(expectedTask));

        final Task resultTask = getTaskUseCase
                .getTask(getTaskProps);

        assertEquals(expectedTask, resultTask);
    }

    @Test
    void storedNoTask_whenGetTask_shouldThrowException() {
        final GetTaskProps getTaskProps = GetTaskPropsFactory
                .getTaskProps(userId);

        when(
                taskRepository
                        .findById(
                                getTaskProps.getTaskId()
                        )
        ).thenThrow(TaskNotFoundException.class);

        assertThrows(
                TaskNotFoundException.class,
                () ->
                        getTaskUseCase
                                .getTask(getTaskProps)
        );
    }
}
