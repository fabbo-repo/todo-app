package com.fabbo.todoapp.modules.task.infrastructure.input.rest;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.MockSpringSecurityFilter;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.domain.data.exceptions.TaskNotFoundException;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.GetTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
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

import static com.fabbo.todoapp.common.utils.TestDataUtils.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.WEB_TEST_PROFILE)
class GetTaskRestControllerTest {
    private static final String GET_TASK_URL = TaskRestController.CONTROLLER_PATH +
            TaskRestController.GET_PATCH_DEL_TASK_SUB_PATH;

    private static final TaskRestMapper TASK_REST_MAPPER = TaskRestMapper.INSTANCE;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    final JwtAuthenticationToken jwtAuthenticationToken = getMockJwtToken(
            randomText()
    );

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @Test
    void givenValidRequest_whenGetTask_shouldReturn200() throws Exception {
        final GetTaskProps getTaskProps = GetTaskPropsFactory
                .getTaskProps(jwtAuthenticationToken);

        final Task mockedTask = new Task(
                randomText(10),
                randomBool(),
                jwtAuthenticationToken
                        .getName()
        );

        when(taskService.getTask(getTaskProps))
                .thenReturn(mockedTask);

        final TaskRestResponse expectedTask = TASK_REST_MAPPER.taskToResponse(
                mockedTask
        );

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_TASK_URL, getTaskProps.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(jwtAuthenticationToken)
        );

        result.andExpect(
                status().isOk()
        );

        final MvcResult mvcResult = result.andReturn();
        final String responseBody = mvcResult.getResponse().getContentAsString();

        assertThat(responseBody)
                .isEqualToIgnoringWhitespace(
                        objectMapper.writeValueAsString(
                                expectedTask
                        )
                );
    }

    @Test
    void givenInvalidTaskId_whenGetTask_shouldReturn400() throws Exception {
        final GetTaskProps getTaskProps = GetTaskPropsFactory
                .getTaskProps(jwtAuthenticationToken);

        when(taskService.getTask(getTaskProps))
                .thenThrow(TaskNotFoundException.class);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(GET_TASK_URL, getTaskProps.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(jwtAuthenticationToken)
        );

        result.andExpect(
                status().isBadRequest()
        );
    }
}
