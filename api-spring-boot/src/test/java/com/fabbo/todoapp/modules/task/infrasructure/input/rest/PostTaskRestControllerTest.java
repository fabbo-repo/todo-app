package com.fabbo.todoapp.modules.task.infrasructure.input.rest;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import com.fabbo.todoapp.common.config.MockSpringSecurityFilter;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.CreateTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.task.infrasructure.input.rest.requests.TaskPostRestRequestFactory;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.TaskRestController;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.mappers.TaskRestMapper;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPostRestRequest;
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

import java.util.Optional;

import static com.fabbo.todoapp.common.utils.TestDataUtils.getMockJwtToken;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.WEB_TEST_PROFILE)
class PostTaskRestControllerTest {
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
    void givenValidRequest_whenPostTask_shouldReturn201() throws Exception {
        final CreateTaskProps createTaskProps = CreateTaskPropsFactory
                .createTaskProps(jwtAuthenticationToken);

        final Task mockedTask = new Task(
                randomText(10),
                randomBool(),
                jwtAuthenticationToken
                        .getName()
        );

        when(taskService.createTask(createTaskProps))
                .thenReturn(mockedTask);

        final TaskRestResponse expectedTask = TASK_REST_MAPPER.taskToResponse(
                mockedTask
        );

        final TaskPostRestRequest request = TaskPostRestRequestFactory
                .taskPostRestRequest(createTaskProps);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(POST_TASK_URL)
                        .content(
                                objectMapper
                                        .writeValueAsString(
                                                request
                                        )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(jwtAuthenticationToken)
        );

        result.andExpect(
                status().isCreated()
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

}
