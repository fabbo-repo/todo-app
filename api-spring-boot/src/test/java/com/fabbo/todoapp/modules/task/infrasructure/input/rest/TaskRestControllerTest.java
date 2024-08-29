package com.fabbo.todoapp.modules.task.infrasructure.input.rest;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import com.fabbo.todoapp.common.config.MockSpringSecurityFilter;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;
import com.fabbo.todoapp.modules.task.domain.props.GetTasksPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.TaskRestController;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.mappers.TaskRestMapper;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.responses.TaskRestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fabbo.todoapp.common.utils.TestDataUtils.getMockJwtToken;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.singleApiPage;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.WEB_TEST_PROFILE)
class TaskRestControllerTest {
    private static final String GET_TASKS_URL = TaskRestController.CONTROLLER_PATH;
    private static final String POST_TASK_URL = TaskRestController.CONTROLLER_PATH;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private AuditorAwareImpl auditorAwareImpl;

    final JwtAuthenticationToken jwtAuthenticationToken = getMockJwtToken(
            randomText()
    );

    private static final TaskRestMapper TASK_REST_MAPPER = TaskRestMapper.INSTANCE;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();

        when(auditorAwareImpl.getToken())
                .thenReturn(Optional.of(jwtAuthenticationToken));
        when(auditorAwareImpl.getTokenOrException())
                .thenReturn(jwtAuthenticationToken);
        when(auditorAwareImpl.getAuthIdOrException())
                .thenReturn(jwtAuthenticationToken.getName());
    }

    @Test
    void givenValidRequest_whenGetTasks_shouldReturn200AndApiPage() throws Exception {
        final List<Task> taskList = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            taskList.add(
                    new Task(
                            randomText(10),
                            randomBool(),
                            jwtAuthenticationToken
                                    .getName()
                    )
            );
        }

        final GetTasksProps getTasksProps = GetTasksPropsFactory
                .getTasksProps(jwtAuthenticationToken);

        final ApiPage<Task> mockedTaskPage = singleApiPage(taskList);
        when(taskService.getTasks(getTasksProps))
                .thenReturn(mockedTaskPage);

        final ApiPage<TaskRestResponse> expectedTaskPage = mockedTaskPage
                .map(TASK_REST_MAPPER::taskToResponse);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_TASKS_URL)
                        .param(
                                "page",
                                String.valueOf(getTasksProps.getPageNum())
                        )
                        .param(
                                "deadlineGte",
                                String.valueOf(
                                        getTasksProps.getFilter()
                                                     .getDeadlineGte()
                                )
                        )
                        .param(
                                "isFinished",
                                String.valueOf(
                                        getTasksProps.getFilter()
                                                     .getIsFinished()
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());

        final MvcResult mvcResult = result.andReturn();
        final String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(
                                expectedTaskPage
                        )
                );
    }

}
