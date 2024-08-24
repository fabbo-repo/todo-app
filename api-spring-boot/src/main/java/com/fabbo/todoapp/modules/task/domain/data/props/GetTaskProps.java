package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetTaskProps {
    private GetUserProps getUserProps;

    private UUID taskId;
}
