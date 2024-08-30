package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class GetTaskProps {
    private GetUserProps getUserProps;

    private UUID taskId;
}
