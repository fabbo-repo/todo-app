package com.fabbo.todoapp.modules.task.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.GetTaskPropsFactory;
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

import java.util.UUID;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class DeleteTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserUseCase getUserUseCase;

    @InjectMocks
    private TaskService deleteTaskUseCase;

    private String userId = "";

    private static final ArgumentCaptor<UUID> TASK_ID_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(UUID.class);

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void givenTaskId_whenDeleteTask_shouldDelete() {
        final GetTaskProps getTaskProps = GetTaskPropsFactory
                .getTaskProps(userId);

        deleteTaskUseCase.deleteTask(getTaskProps);

        verify(taskRepository)
                .deleteById(TASK_ID_ARGUMENT_CAPTOR.capture());

        assertEquals(
                getTaskProps.getTaskId(),
                TASK_ID_ARGUMENT_CAPTOR.getValue()
        );
    }
}
