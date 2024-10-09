package com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.requests;

import com.fabbo.todoapp.common.config.TimeZoneConfig;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountPatchRequest {
    @Size(min = 1, max = 20)
    private String username;

    @Size(max = 300)
    private String description;

    @JsonFormat(pattern = TimeZoneConfig.DATE_ISO_FORMAT)
    private LocalDate birthDate;

    @Size(min = 2, max = 5)
    private String locale;
}
