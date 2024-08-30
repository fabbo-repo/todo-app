package com.fabbo.todoapp.modules.task.domain.data.props;

import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class CreateTaskProps {
    private GetUserProps getUserProps;

    private String title;

    private String description;

    private LocalDateTime deadline;
}
