package com.fabbo.todoapp.modules.user.domain.data.models;

import com.fabbo.todoapp.common.data.mappers.Default;
import com.fabbo.todoapp.common.data.models.ApiImage;
import com.fabbo.todoapp.common.data.models.AuditableData;
import com.fabbo.todoapp.common.utils.ImageUtils;
import com.fabbo.todoapp.modules.user.domain.data.exceptions.AccountEmailNotVerifiedException;
import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {
    private String id;

    private String username;

    private String description;

    private String locale;

    private ApiImage image;

    private AuditableData auditableData;

    public static final String EMAIL_VERIFIED_CLAIM = "email_verified";

    public static final String IMG_PREFIX_PATH = "profile-image";

    @Default
    public User(final String id) {
        this.id = id;
    }

    public User(
            final JwtAuthenticationToken token
    ) {
        this.setId(token.getName());
    }

    public void update(final UpdateUserProps updateUserDto) {
        if (updateUserDto.getUsername() != null) {
            setUsername(updateUserDto.getUsername());
        }
        if (updateUserDto.getDescription() != null) {
            setDescription(updateUserDto.getDescription());
        }
        if (updateUserDto.getLocale() != null) {
            setLocale(updateUserDto.getLocale());
        }
        if (updateUserDto.getImageFile() != null) {
            if (getImage() == null || getImage().getId() == null) {
                final UUID imageId = UUID.randomUUID();
                final ApiImage userImage = new ApiImage(
                        imageId,
                        ImageUtils.generatePath(
                                IMG_PREFIX_PATH
                                + "/"
                                + getId(),
                                imageId.toString(),
                                updateUserDto.getImageFile()
                        )
                );
                setImage(userImage);
            } else {
                getImage().setPath(
                        ImageUtils.generatePath(
                                IMG_PREFIX_PATH
                                + "/"
                                + getId(),
                                getImage().getId().toString(),
                                updateUserDto.getImageFile()
                        )
                );
            }
        }
    }

    public static void validateToken(
            @NonNull final JwtAuthenticationToken token
    ) {
        final Map<String, Object> claims = token
                .getToken()
                .getClaims();
        if (claims.containsKey(EMAIL_VERIFIED_CLAIM)
            && !(boolean) claims.get(EMAIL_VERIFIED_CLAIM)) {
            throw new AccountEmailNotVerifiedException();
        }
    }
}
