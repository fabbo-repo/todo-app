package com.fabbo.todoapp.modules.user.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.application.repositories.UserRepository;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.domain.data.props.UpdateUserProps;
import com.fabbo.todoapp.modules.user.domain.props.UpdateUserPropsFactory;
import com.fabbo.todoapp.modules.user.domain.services.UserService;
import com.fabbo.todoapp.modules.user.infrastructure.output.clients.UserImageClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.fabbo.todoapp.common.utils.TestDataUtils.randomText;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private UserImageClient userImageClient;

    @InjectMocks
    private UserService updateUserUseCase;

    private static final ArgumentCaptor<User> USER_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(User.class);

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void givenValidProps_whenUpdateUser_shouldSaveValidUser() {
        final UpdateUserProps updateUserProps = UpdateUserPropsFactory
                .updateUserProps(userId);

        final User storedUser = new User(userId);

        final User expectedUser = new User(userId);
        expectedUser.setUsername(updateUserProps.getUsername());
        expectedUser.setDescription(updateUserProps.getDescription());
        expectedUser.setLocale(updateUserProps.getLocale());

        when(
                userRepository
                        .findById(updateUserProps.getId())
        ).thenReturn(
                Optional.of(storedUser)
        );

        updateUserUseCase.updateUser(updateUserProps);

        verify(userRepository)
                .save(
                        USER_ARGUMENT_CAPTOR.capture()
                );

        assertThat(expectedUser)
                .usingRecursiveComparison()
                .ignoringFields("image", "auditableData")
                .isEqualTo(USER_ARGUMENT_CAPTOR.getValue());
    }
}
