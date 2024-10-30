package com.fabbo.todoapp.modules.task.infrastructure.input.rest;

import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import com.fabbo.todoapp.common.config.TimeZoneConfig;
import com.fabbo.todoapp.common.data.models.ApiPage;
import com.fabbo.todoapp.modules.task.application.usecases.CreateTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.DeleteTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.GetTaskUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.GetTasksUseCase;
import com.fabbo.todoapp.modules.task.application.usecases.UpdateTaskUseCase;
import com.fabbo.todoapp.modules.task.domain.data.models.Task;
import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import com.fabbo.todoapp.modules.task.domain.data.props.FilterTasksProps;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;
import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.mappers.TaskRestMapper;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPatchRestRequest;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.requests.TaskPostRestRequest;
import com.fabbo.todoapp.modules.task.infrastructure.input.rest.data.responses.TaskRestResponse;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @DateTimeFormat(pattern = TimeZoneConfig.DATE_TIME_ISO_FORMAT) @RequestParam(required = false)
            final LocalDateTime deadlineGte,
            @RequestParam(defaultValue = "0", required = false) @Min(0) final Integer pageNum,
            @RequestParam(defaultValue = "10", required = false) @Min(1) @Max(20) final Integer pageSize
    ) {
        final FilterTasksProps filter = new FilterTasksProps();
        filter.setIsFinished(isFinished);
        filter.setDeadlineGte(deadlineGte);

        final GetTasksProps getTasksProps = new GetTasksProps(
                pageNum,
                pageSize,
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
