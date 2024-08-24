package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateTaskProps extends GetTaskProps {

    private String title;

    private String description;

    private Boolean isFinished;

    private LocalDateTime deadline;

    public UpdateTaskProps(
            final GetUserProps getUserProps,
            final UUID taskId
    ) {
        super(getUserProps, taskId);
    }
}
