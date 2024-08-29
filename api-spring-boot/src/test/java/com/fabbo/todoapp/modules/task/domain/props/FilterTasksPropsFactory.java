package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.FilterTasksProps;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomPastDateTime;

public class FilterTasksPropsFactory {
    public static FilterTasksProps filterTasksProps() {
        final FilterTasksProps filterTasksProps = new FilterTasksProps();
        filterTasksProps.setIsFinished(randomBool());
        filterTasksProps.setDeadlineGte(
                randomPastDateTime()
        );
        return filterTasksProps;
    }
}
