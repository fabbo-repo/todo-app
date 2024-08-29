package com.fabbo.todoapp.modules.task.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.CreateTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class CreateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserUseCase getUserUseCase;

    @InjectMocks
    private TaskService createTaskUseCase;

    private static final ArgumentCaptor<Task> TASK_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(Task.class);

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void givenValidProps_whenCreateTask_shouldStoreValidTask() {
        final CreateTaskProps createTaskProps = CreateTaskPropsFactory
                .createTaskProps(userId);

        final Task expectedTask = new Task(
                createTaskProps.getTitle(),
                true,
                userId
        );
        expectedTask.setDescription(
                createTaskProps.getDescription()
        );
        expectedTask.setDeadline(
                createTaskProps.getDeadline()
        );

        when(
                getUserUseCase
                        .getUser(
                                createTaskProps
                                        .getGetUserProps()
                        )
        ).thenReturn(
                new User(userId)
        );
        when(
                taskRepository
                        .save(expectedTask)
        ).thenReturn(
                expectedTask
        );

        final Task resultTask = createTaskUseCase.createTask(createTaskProps);

        verify(taskRepository)
                .save(
                        TASK_ARGUMENT_CAPTOR.capture()
                );

        assertEquals(expectedTask, TASK_ARGUMENT_CAPTOR.getValue());
        assertEquals(expectedTask, resultTask);
    }
}
