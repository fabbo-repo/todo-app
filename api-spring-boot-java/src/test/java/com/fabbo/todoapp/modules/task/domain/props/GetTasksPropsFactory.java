package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.GetTasksProps;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.fabbo.todoapp.modules.user.domain.props.GetUserPropsFactory.getJwtUserProps;

public class GetTasksPropsFactory {
    public static GetTasksProps getTasksProps(
            final String userId
    ) {
        return new GetTasksProps(
                0,
                10,
                getJwtUserProps(userId),
                FilterTasksPropsFactory.filterTasksProps()
        );
    }

    public static GetTasksProps getTasksProps(
            final JwtAuthenticationToken jwt
    ) {
        return new GetTasksProps(
                0,
                10,
                new GetUserProps(jwt),
                FilterTasksPropsFactory.filterTasksProps()
        );
    }
}
