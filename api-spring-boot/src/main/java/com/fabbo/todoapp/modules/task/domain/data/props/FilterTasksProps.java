package com.fabbo.todoapp.modules.task.domain.data.props;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class FilterTasksProps {
    private Boolean isFinished;

    private LocalDateTime deadlineGte;
}
