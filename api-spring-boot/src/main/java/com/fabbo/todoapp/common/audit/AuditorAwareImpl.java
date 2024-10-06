package com.fabbo.todoapp.common.audit;

import com.fabbo.todoapp.common.data.exceptions.UnauthorizedException;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    public static Optional<JwtAuthenticationToken> getToken() {
        final Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();
        if (authentication == null) {
            return Optional.empty();
        }
        return Optional.of((JwtAuthenticationToken) authentication);
    }

    public static JwtAuthenticationToken getTokenOrException() {
        return getToken()
                .orElseThrow(
                        UnauthorizedException::new
                );
    }

    public static String getAuthIdOrException() {
        return getToken()
                .orElseThrow(
                        UnauthorizedException::new
                )
                .getName();
    }

    @Override
    @NonNull
    public Optional<String> getCurrentAuditor() {
        return getToken()
                .map(JwtAuthenticationToken::getName);
    }
}
