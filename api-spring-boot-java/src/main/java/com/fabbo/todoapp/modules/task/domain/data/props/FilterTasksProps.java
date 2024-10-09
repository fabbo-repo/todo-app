package com.fabbo.todoapp.modules.task.domain.data.props;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FilterTasksProps {
    private Boolean isFinished;

    private LocalDateTime deadlineGte;
}
