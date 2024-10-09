package com.fabbo.todoapp.common.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;

@Slf4j
public class AppJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter
            = new JwtGrantedAuthoritiesConverter();

    @Override
    public AbstractAuthenticationToken convert(
            @NonNull final Jwt jwt
    ) {
        final Collection<GrantedAuthority> authorities = this.jwtGrantedAuthoritiesConverter.convert(jwt);
        final String principalClaimValue = jwt.getSubject();
        return new JwtAuthenticationToken(jwt, authorities, principalClaimValue);
    }
}
