package com.fabbo.todoapp.modules.task.infrastructure.input.rest;

import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import com.fabbo.todoapp.common.config.TimeZoneConfig;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.application.usecases.*;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.*;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.mappers.TaskRestMapper;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPatchRestRequest;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPostRestRequest;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.responses.TaskRestResponse;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping(path = TaskRestController.CONTROLLER_PATH)
@Validated
@Tag(name = TaskRestController.SWAGGER_TAG)
@RequiredArgsConstructor
public class TaskRestController {
    public static final String SWAGGER_TAG = "Task API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/v1/task";
    @SuppressWarnings("squid:S1075")
    public static final String GET_PATCH_DEL_TASK_SUB_PATH = "/{id}";

    private static final TaskRestMapper TASK_REST_MAPPER = TaskRestMapper.INSTANCE;

    private final GetTaskUseCase getTaskUseCase;

    private final GetTasksUseCase getTasksUseCase;

    private final CreateTaskUseCase createTaskUseCase;

    private final UpdateTaskUseCase updateTaskUseCase;

    private final DeleteTaskUseCase deleteTaskUseCase;

    @GetMapping(GET_PATCH_DEL_TASK_SUB_PATH)
    public ResponseEntity<TaskRestResponse> getTask(
            @PathVariable final UUID id
    ) {
        final Task task = getTaskUseCase.getTask(
                new GetTaskProps(
                        new GetUserProps(AuditorAwareImpl.getTokenOrException()),
                        id
                )
        );
        return ResponseEntity.ok(
                TASK_REST_MAPPER.taskToResponse(task)
        );
    }

    @GetMapping
    public ResponseEntity<ApiPage<TaskRestResponse>> getTasks(
            @RequestParam(required = false) final Boolean isFinished,
            @DateTimeFormat(pattern = TimeZoneConfig.DATE_TIME_ISO_FORMAT) @RequestParam(required = false) final LocalDateTime deadlineGte,
            @RequestParam(defaultValue = "0") @Min(0) final Integer page
    ) {
        final FilterTasksProps filter = new FilterTasksProps();
        filter.setIsFinished(isFinished);
        filter.setDeadlineGte(deadlineGte);

        final GetTasksProps getTasksProps = new GetTasksProps(
                page,
                new GetUserProps(
                        AuditorAwareImpl
                                .getTokenOrException()
                ),
                filter
        );

        final ApiPage<Task> taskPage = getTasksUseCase.getTasks(
                getTasksProps
        );
        return ResponseEntity.ok(
                taskPage.map(TASK_REST_MAPPER::taskToResponse)
        );
    }

    @PostMapping
    public ResponseEntity<TaskRestResponse> createTask(
            @RequestBody @Valid final TaskPostRestRequest request
    ) {
        final Task newTask = createTaskUseCase.createTask(
                new CreateTaskProps(
                        new GetUserProps(
                                AuditorAwareImpl
                                        .getTokenOrException()
                        ),
                        request.getTitle(),
                        request.getDescription(),
                        request.getDeadline()
                )
        );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TASK_REST_MAPPER.taskToResponse(newTask));
    }

    @DeleteMapping(GET_PATCH_DEL_TASK_SUB_PATH)
    public ResponseEntity<Void> deleteTask(@PathVariable final UUID id) {
        deleteTaskUseCase.deleteTask(
                new GetTaskProps(
                        new GetUserProps(
                                AuditorAwareImpl
                                        .getTokenOrException()
                        ),
                        id
                )
        );
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping(GET_PATCH_DEL_TASK_SUB_PATH)
    public ResponseEntity<Void> patchTask(
            @PathVariable final UUID id,
            @RequestBody final TaskPatchRestRequest request
    ) {
        final UpdateTaskProps updateTaskProps = new UpdateTaskProps(
                new GetUserProps(
                        AuditorAwareImpl
                                .getTokenOrException()
                ),
                id
        );
        updateTaskProps.setTitle(request.getTitle());
        updateTaskProps.setDescription(request.getDescription());
        updateTaskProps.setIsFinished(request.getIsFinished());
        updateTaskProps.setDeadline(request.getDeadline());
        updateTaskUseCase.updateTask(
                updateTaskProps
        );
        return ResponseEntity.noContent().build();
    }
}
