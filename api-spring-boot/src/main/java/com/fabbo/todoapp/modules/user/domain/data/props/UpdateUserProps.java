package com.fabbo.todoapp.modules.user.domain.data.props;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateUserProps extends GetUserProps {

    private String username;

    private String description;

    private String locale;

    private MultipartFile imageFile;

    public UpdateUserProps(
            final JwtAuthenticationToken token
    ) {
        super(token);
    }
}
