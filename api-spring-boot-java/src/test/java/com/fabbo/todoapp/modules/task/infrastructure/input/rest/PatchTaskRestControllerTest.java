package com.fabbo.todoapp.modules.task.infrastructure.input.rest;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.MockSpringSecurityFilter;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.task.domain.data.exceptions.TaskNotFoundException;
import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import com.fabbo.todoapp.modules.task.domain.props.UpdateTaskPropsFactory;
import com.fabbo.todoapp.modules.task.domain.services.TaskService;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.requests.TaskPatchRestRequestFactory;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPatchRestRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.fabbo.todoapp.common.utils.TestDataUtils.getMockJwtToken;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.WEB_TEST_PROFILE)
class PatchTaskRestControllerTest {
    private static final String PATCH_TASK_URL = TaskRestController.CONTROLLER_PATH +
            TaskRestController.GET_PATCH_DEL_TASK_SUB_PATH;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private static final ArgumentCaptor<UpdateTaskProps> UPDATE_TASK_PROPS_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(UpdateTaskProps.class);

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
    void givenValidRequest_whenPatchTask_shouldReturn204() throws Exception {
        final UpdateTaskProps updateTaskProps = UpdateTaskPropsFactory
                .updateTaskProps(jwtAuthenticationToken);

        final TaskPatchRestRequest request = TaskPatchRestRequestFactory
                .taskPatchRestRequest(updateTaskProps);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(PATCH_TASK_URL, updateTaskProps.getTaskId())
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
                status().isNoContent()
        );

        verify(taskService)
                .updateTask(
                        UPDATE_TASK_PROPS_ARGUMENT_CAPTOR
                                .capture()
                );

        assertEquals(updateTaskProps, UPDATE_TASK_PROPS_ARGUMENT_CAPTOR.getValue());
    }

    @Test
    void givenInvalidTaskId_whenPatchTask_shouldReturn400() throws Exception {
        final UpdateTaskProps updateTaskProps = UpdateTaskPropsFactory
                .updateTaskProps(jwtAuthenticationToken);

        when(taskService.updateTask(updateTaskProps))
                .thenThrow(TaskNotFoundException.class);

        final ResultActions result = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(PATCH_TASK_URL, updateTaskProps.getTaskId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(jwtAuthenticationToken)
        );

        result.andExpect(
                status().isBadRequest()
        );
    }
}
