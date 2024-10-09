package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.GetTaskProps;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomUuid;

public class GetTaskPropsFactory {
    public static GetTaskProps getTaskProps(
            final String userId
    ) {
        return new GetTaskProps(
                new GetUserProps(userId),
                randomUuid()
        );
    }

    public static GetTaskProps getTaskProps(
            final JwtAuthenticationToken jwt
    ) {
        return new GetTaskProps(
                new GetUserProps(jwt),
                randomUuid()
        );
    }
}
