package com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountRestResponse {
    private String id;

    private String username;

    private String description;

    private String locale;
}
