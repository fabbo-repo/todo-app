package com.fabbo.todoapp.modules.task.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.common.data.exceptions.ApiPageNotFoundException;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.application.repositories.TaskRepository;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;
import com.fabbo.todoapp.modules.task.domain.props.GetTasksPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.singleApiPage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class GetTasksUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private GetUserUseCase getUserUseCase;

    @InjectMocks
    private TaskService getTasksUseCase;

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void storedMoreThanOneTask_whenGetTasks_shouldReturnValidPage() {
        final GetTasksProps getTasksProps = GetTasksPropsFactory
                .getTasksProps(userId);

        final List<Task> taskList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            taskList.add(
                    new Task(
                            randomText(10),
                            randomBool(),
                            userId
                    )
            );
        }

        when(
                taskRepository
                        .findAll(
                                getTasksProps.getFilter(),
                                getTasksProps
                        )
        ).thenReturn(
                singleApiPage(taskList)
        );

        final ApiPage<Task> expectedApiPage = singleApiPage(taskList);
        final ApiPage<Task> resultApiPage = getTasksUseCase
                .getTasks(getTasksProps);

        assertEquals(expectedApiPage, resultApiPage);
    }

    @Test
    void storedNoTask_whenGetTasks_shouldReturnEmptyPage() {
        final GetTasksProps getTasksProps = GetTasksPropsFactory
                .getTasksProps(userId);

        when(
                taskRepository
                        .findAll(
                                getTasksProps.getFilter(),
                                getTasksProps
                        )
        ).thenReturn(
                singleApiPage(Collections.emptyList())
        );

        final ApiPage<Task> expectedApiPage = singleApiPage(
                Collections.emptyList()
        );
        final ApiPage<Task> resultApiPage = getTasksUseCase
                .getTasks(getTasksProps);

        assertEquals(expectedApiPage, resultApiPage);
    }

    @Test
    void givenWrongPageNum_whenGetTasks_shouldThrowPageNotFound() {
        final GetTasksProps getTasksProps = GetTasksPropsFactory
                .getTasksProps(userId);

        when(
                taskRepository
                        .findAll(
                                getTasksProps.getFilter(),
                                getTasksProps
                        )
        ).thenThrow(ApiPageNotFoundException.class);

        assertThrows(
                ApiPageNotFoundException.class,
                () ->
                        getTasksUseCase
                                .getTasks(getTasksProps)
        );
    }
}
