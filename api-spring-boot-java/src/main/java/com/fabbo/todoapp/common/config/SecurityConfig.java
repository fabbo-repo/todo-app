package com.fabbo.todoapp.common.config;

import com.fabbo.todoapp.common.handlers.AppJwtAuthenticationConverter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final ApplicationContext applicationContext
    ) throws Exception {
        http
                .cors(Customizer.withDefaults()) // by default a bean with "corsConfigurationSource" as name will be used
                .csrf(
                        httpSecurityCsrfConfigurer -> {
                        }
                )
                .sessionManagement(
                        session ->
                                session
                                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorizationManagerRequestMatcherRegistry ->
                                authorizationManagerRequestMatcherRegistry
                                        .requestMatchers(
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**"
                                        ).permitAll()
                                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(
                        httpSecurityOAuth2ResourceServerConfigurer ->
                                httpSecurityOAuth2ResourceServerConfigurer
                                        .jwt(jwtConfigurer ->
                                                     jwtConfigurer
                                                             .jwtAuthenticationConverter(
                                                                     jwtAuthenticationConverter())
                                        )
                );
        return http.build();
    }

    public Converter<Jwt, AbstractAuthenticationToken> jwtAuthenticationConverter() {
        return new AppJwtAuthenticationConverter();
    }
}
