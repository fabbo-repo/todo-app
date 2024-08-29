package com.fabbo.todoapp.modules.user.domain.props;

import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomJwt;
import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;

public class GetUserPropsFactory {
    public static GetUserProps getIdUserProps() {
        return new GetUserProps(
                randomText()
        );
    }

    public static GetUserProps getIdUserProps(
            final String userId
    ) {
        return new GetUserProps(userId);
    }

    public static GetUserProps getJwtUserProps(
            final String userId
    ) {
        return new GetUserProps(
                randomJwt(userId)
        );
    }


    public static GetUserProps getJwtUserProps(
            final JwtAuthenticationToken jwt
    ) {
        return new GetUserProps(jwt);
    }
}
