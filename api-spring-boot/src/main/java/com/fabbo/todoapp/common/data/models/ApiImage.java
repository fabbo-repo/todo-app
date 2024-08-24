package com.fabbo.todoapp.common.data.models;

import com.fabbo.todoapp.common.data.mappers.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ApiImage {

    private UUID id;

    private String path;

    private URL url;

    private LocalDateTime createdAt;

    private LocalDateTime urlExpiration;

    @Default
    public ApiImage(
            final UUID id,
            final String path
    ) {
        setId(id);
        setPath(path);
        this.createdAt = LocalDateTime.now();
    }

    public ApiImage(
            final UUID id,
            final String path,
            final LocalDateTime createdAt
    ) {
        setId(id);
        setPath(path);
        setCreatedAt(createdAt);
    }

    public void setImageUrl(
            @NonNull final URL url,
            @NonNull final LocalDateTime urlExpiration
    ) {
        this.url = url;
        this.urlExpiration = urlExpiration;
    }
}
