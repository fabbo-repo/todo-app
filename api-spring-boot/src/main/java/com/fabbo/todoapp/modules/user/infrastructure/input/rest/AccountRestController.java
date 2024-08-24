package com.fabbo.todoapp.modules.user.infrastructure.input.rest;

import com.fabbo.todoapp.common.annotations.IsImageFormat;
import com.fabbo.todoapp.common.annotations.MaxSizeFile;
import com.fabbo.todoapp.common.annotations.NotEmptyFile;
import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import com.fabbo.todoapp.modules.user.application.usecases.GetUserUseCase;
import com.fabbo.todoapp.modules.user.application.usecases.UpdateUserUseCase;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;
import com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.mappers.AccountRestMapper;
import com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.requests.AccountPatchRequest;
import com.fabbo.todoapp.modules.user.infrastructure.input.rest.data.responses.AccountRestResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = AccountRestController.CONTROLLER_PATH)
@Validated
@Tag(name = AccountRestController.SWAGGER_TAG)
@RequiredArgsConstructor
public class AccountRestController {
    public static final String SWAGGER_TAG = "Account API";

    @SuppressWarnings("squid:S1075")
    public static final String CONTROLLER_PATH = "/v1/user";

    @SuppressWarnings("squid:S1075")
    public static final String POST_ACCOUNT_IMAGE_SUB_PATH = "/image";

    private final AuditorAwareImpl auditorAware;

    private final GetUserUseCase getUserUseCase;

    private final UpdateUserUseCase updateUserUseCase;

    private static final AccountRestMapper ACCOUNT_REST_MAPPER = AccountRestMapper.INSTANCE;

    @GetMapping
    public AccountRestResponse getAccount() {
        final JwtAuthenticationToken token = auditorAware.getTokenOrException();
        return ACCOUNT_REST_MAPPER.userToResponse(
                getUserUseCase.getUser(
                        new GetUserProps(token)
                )
        );
    }

    @PatchMapping
    public ResponseEntity<Void> patchAccount(
            @Valid @RequestBody final AccountPatchRequest request
    ) {
        final JwtAuthenticationToken token = auditorAware.getTokenOrException();
        final UpdateUserProps updateUserDto = new UpdateUserProps(token);
        updateUserDto.setUsername(request.getUsername());
        updateUserDto.setDescription(request.getDescription());
        updateUserDto.setLocale(request.getLocale());
        updateUserUseCase.updateUser(
                updateUserDto
        );
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = POST_ACCOUNT_IMAGE_SUB_PATH, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImage(
            @IsImageFormat
            @NotEmptyFile
            @MaxSizeFile
            @RequestPart final MultipartFile image
    ) {
        final JwtAuthenticationToken token = auditorAware.getTokenOrException();
        final UpdateUserProps updateUserDto = new UpdateUserProps(token);
        updateUserDto.setImageFile(image);
        updateUserUseCase.updateUser(
                updateUserDto
        );
        return ResponseEntity
                .noContent()
                .build();
    }
}
