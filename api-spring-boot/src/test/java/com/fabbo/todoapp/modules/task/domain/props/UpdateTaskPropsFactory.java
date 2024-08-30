package com.fabbo.todoapp.modules.task.domain.props;

import com.fabbo.todoapp.modules.task.domain.data.props.UpdateTaskProps;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomBool;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomFutureDateTime;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomUuid;
import static com.fabbo.todoapp.modules.user.domain.props.GetUserPropsFactory.getJwtUserProps;

public class UpdateTaskPropsFactory {
    public static UpdateTaskProps updateTaskProps(
            final JwtAuthenticationToken jwt
    ) {
        final UpdateTaskProps updateTaskProps = new UpdateTaskProps(
                getJwtUserProps(jwt),
                randomUuid()
        );
        updateTaskProps.setTitle(
                randomText(50)
        );
        updateTaskProps.setDescription(
                randomText(300)
        );
        updateTaskProps.setIsFinished(
                randomBool()
        );
        updateTaskProps.setDeadline(
                randomFutureDateTime()
        );
        return updateTaskProps;
    }
}
