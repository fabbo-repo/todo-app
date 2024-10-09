package com.fabbo.todoapp.modules.user.application;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.config.ReplaceUnderscoresAndCamelCase;
import com.fabbo.todoapp.modules.user.application.repositories.UserImageRepository;
import com.fabbo.todoapp.modules.user.application.repositories.UserRepository;
import com.fabbo.todoapp.modules.user.domain.data.models.User;
import com.fabbo.todoapp.modules.user.domain.data.props.GetUserProps;
import com.fabbo.todoapp.modules.user.domain.props.GetUserPropsFactory;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscoresAndCamelCase.class)
@ActiveProfiles(TodoappApplication.TEST_PROFILE)
class GetUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserImageRepository userImageRepository;

    @Mock
    private UserImageClient userImageClient;

    @InjectMocks
    private UserService getUserUseCase;

    private static final ArgumentCaptor<User> USER_ARGUMENT_CAPTOR = ArgumentCaptor
            .forClass(User.class);

    private String userId = "";

    @BeforeEach
    void setupEach() {
        this.userId = randomText();
    }

    @Test
    void givenValidIdProps_whenGetUser_shouldReturnStoredUser() {
        final GetUserProps getUserProps = GetUserPropsFactory
                .getIdUserProps(userId);

        final User expectedUser = new User(userId);

        when(
                userRepository
                        .findById(
                                getUserProps.getId()
                        )
        ).thenReturn(
                Optional.of(expectedUser)
        );

        final User resultUser = getUserUseCase.getUser(
                getUserProps
        );

        assertEquals(expectedUser, resultUser);
    }

    @Test
    void givenValidTokenProps_whenGetUser_shouldStoreUser() {
        final GetUserProps getUserProps = GetUserPropsFactory
                .getJwtUserProps(userId);

        final User expectedUser = new User(userId);

        when(
                userRepository
                        .findById(
                                getUserProps.getId()
                        )
        ).thenReturn(Optional.empty());

        when(
                userRepository
                        .save(expectedUser)
        ).thenReturn(
                expectedUser
        );

        final User resultUser = getUserUseCase.getUser(getUserProps);

        verify(userRepository)
                .save(
                        USER_ARGUMENT_CAPTOR.capture()
                );

        assertEquals(expectedUser, USER_ARGUMENT_CAPTOR.getValue());
        assertEquals(expectedUser, resultUser);
    }
}
