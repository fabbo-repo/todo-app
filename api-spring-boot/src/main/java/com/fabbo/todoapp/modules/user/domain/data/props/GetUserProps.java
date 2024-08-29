package com.fabbo.todoapp.modules.user.domain.data.props;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class GetUserProps {
    private String id;

    private boolean createIfNotExists;

    private JwtAuthenticationToken token;

    public GetUserProps(final String id) {
        this.id = id;
        this.createIfNotExists = false;
    }

    public GetUserProps(JwtAuthenticationToken token) {
        this.id = token.getName();
        this.createIfNotExists = true;
        this.token = token;
    }
}
