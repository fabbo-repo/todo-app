package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.CreateTaskProps;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomFutureDateTime;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static com.fabbo.todoapp.modules.user.domain.props.GetUserPropsFactory.getJwtUserProps;

public class CreateTaskPropsFactory {
    public static CreateTaskProps createTaskProps(
            final String userId
    ) {
        return new CreateTaskProps(
                getJwtUserProps(userId),
                randomText(50),
                randomText(300),
                randomFutureDateTime()
        );
    }

    public static CreateTaskProps createTaskProps(
            final JwtAuthenticationToken jwt
    ) {
        return new CreateTaskProps(
                getJwtUserProps(jwt),
                randomText(50),
                randomText(300),
                randomFutureDateTime()
        );
    }
}
