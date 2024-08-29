package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;

import static com.fabbo.todoapp.modules.user.domain.props.GetUserPropsFactory.getJwtUserProps;

public class GetTasksPropsFactory {
    public static GetTasksProps getTasksProps(
            final String userId
    ) {
        return new GetTasksProps(
                0,
                getJwtUserProps(userId),
                FilterTasksPropsFactory.filterTasksProps()
        );
    }
}
